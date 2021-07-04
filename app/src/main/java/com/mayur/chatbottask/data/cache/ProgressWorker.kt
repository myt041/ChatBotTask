package com.mayur.chatbottask.data.cache

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mayur.chatbottask.ui.chat.ChatViewModel
import com.mayur.chatbottask.util.Const


class ProgressWorker(
    context: Context,
    private val workerParameters: WorkerParameters
) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        //fecthing the paramaters to make the network call
        val userId = workerParameters.inputData.getString(Const.USER_ID)
        val message = workerParameters.inputData.getString(Const.MESSAGE)


//        viewModel.getBotReply(message.orEmpty(),userId.orEmpty())

        return Result.success()

    }
}

