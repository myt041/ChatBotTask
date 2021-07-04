package com.mayur.chatbottask.ui.chat

import android.provider.Telephony.Mms.Part.CONTENT_ID
import android.util.Log
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.mayur.chatbottask.data.cache.MessageCache
import com.mayur.chatbottask.data.cache.ProgressWorker
import com.mayur.chatbottask.data.network.response.chatBotResponse
import com.mayur.chatbottask.data.repositories.ChatRepository
import com.mayur.chatbottask.util.ApiException
import com.mayur.chatbottask.util.Const
import com.mayur.chatbottask.util.StateListener
import com.rommansabbir.networkx.isInternetConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) :
    ViewModel(), Observable {

    val _botResponse = MutableLiveData<chatBotResponse>()
    val botResponse: LiveData<chatBotResponse> = _botResponse
    val previousMessages = MutableLiveData<ArrayList<MessageCache>>()

    val unAsyncMessages = MutableLiveData<ArrayList<MessageCache>>()


    var stateListener: StateListener? = null


    fun getBotReply(msg: String, userId: String) {

        if (isInternetConnected()) {

            stateListener?.onLoading()

            viewModelScope.launch {
                try {
                    val response =
                        repository.getBotReply(Const.apiKey, msg, Const.chatBotID, userId)

                    _botResponse.postValue(response)

                } catch (e: ApiException) {
                    stateListener?.onFailure(e.message!!)
                    return@launch
                } catch (e: UnknownHostException) {
                    stateListener?.onFailure("No internet connection.")
                    return@launch
                } catch (e: Exception) {
                    stateListener?.onFailure("Failed")
                    return@launch
                }

            }
        } else {
//            scheduleBotReply(userId,msg)
        }
    }

    fun scheduleBotReply(userId: String,msg : String) {
        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val progressData = workDataOf(Const.USER_ID to userId, Const.MESSAGE to msg)

        val request: OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<ProgressWorker>()
                .setConstraints(constraints)
                .setInputData(progressData)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 20, TimeUnit.SECONDS)
                .build()

        WorkManager.getInstance().enqueue(request)
    }

    fun insertUserMessage(message: MessageCache) {
        viewModelScope.launch {
            repository.insertMessage(message)

            getBotReply(message.contents, message.userId)
        }
    }

    fun performNetworkBackOperation() {
        viewModelScope.launch {
            val unAsyncMsg = repository.fetchUnAsyncMessages()
//            unAsyncMessages.postValue(ArrayList(unAsyncMsg))

            val uList = arrayListOf<MessageCache>()
            uList.addAll(unAsyncMsg)
            if (uList.size > 0) {

                val list = uList.filter { it.userId == ChatFragment.meUser.id }

                if (list.isNotEmpty()) {
                    val msg = list.last()
                    getBotReply(msg.contents, msg.userId)
                }


            }
        }
    }

    fun insertBotMessage(message: MessageCache) {
        viewModelScope.launch {
            repository.insertMessage(message)
        }
    }

    fun fetchPreviousMessages(userId: String) {
        viewModelScope.launch {
            val messages = repository.fetchPreviousMessage(userId)
            previousMessages.postValue(ArrayList(messages))
        }
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}