package com.mayur.chatbottask.ui.chat

import androidx.databinding.Observable
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.chatbottask.data.repositories.ChatRepository
import com.mayur.chatbottask.util.ApiException
import com.mayur.chatbottask.util.StateListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: ChatRepository) :
    ViewModel(), Observable {

    val botResponse = MutableLiveData<chatBotResponse>()


    var stateListener: StateListener? = null


    fun getBotReply(msg: String, userId: String) {
        stateListener?.onLoading()

        viewModelScope.launch {
            try {
                val response =
                    repository.getBotReply(repository.apiKey, msg, repository.chatBotID, userId)

                botResponse.postValue(response)

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
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}