package com.mayur.chatbottask.ui.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mayur.chatbottask.R
import com.mayur.chatbottask.databinding.ActivityChatBinding
import com.mayur.chatbottask.util.StateListener
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.messages.MessageHolders
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesListAdapter
import com.stfalcon.chatkit.utils.DateFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatActivity : AppCompatActivity(), MessageInput.InputListener,
    MessagesListAdapter.OnLoadMoreListener, StateListener {

    val senderId = "45544"
    private lateinit var binding: ActivityChatBinding

    private val viewModel: ChatViewModel by viewModels()
    var messagesAdapter: MessagesListAdapter<ChatMessage>? = null
    private var imageLoader: ImageLoader? = null
    lateinit var meUser: User
    lateinit var youUser: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        viewModel.botResponse.observe(this, {
            if (it.success == 1) {
                val botMsg = it.message.message

                if (botMsg.isBlank()) {
                    return@observe
                }
                val timeStamp = System.currentTimeMillis()

                var message = ChatMessage(
                    youUser,
                    botMsg,
                    timeStamp.toString(),
                    0,
                    timeStamp.toString()
                )
                messagesAdapter?.addToStart(message, true)
            }
        })

        meUser = User(
            senderId,
            "Mayur",
            "",
            false
        )

        youUser = User(
            "63906",
            "ChatBot",
            "",
            false
        )

        initAdapter()

        binding.input.setInputListener { input ->
            sendMessage(input.toString())
            false
        }

    }


    private fun sendMessage(textData: String) {
        val text = textData.trim()
        if (text.isBlank()) {
            return
        }
        binding.input.inputEditText.setText("")

        val timeStamp = System.currentTimeMillis()


        var message = ChatMessage(
            meUser,
            textData,
            timeStamp.toString(),
            0,
            timeStamp.toString()
        )
        messagesAdapter?.addToStart(message, true)

        viewModel.getBotReply(textData, senderId)
    }

    override fun onSubmit(input: CharSequence?): Boolean {
        return true
    }

    override fun onLoadMore(page: Int, totalItemsCount: Int) {
    }


    private fun initAdapter() {

        class CustomIncomingMessageViewHolder(itemView: View?) :
            MessageHolders.IncomingTextMessageViewHolder<IMessage>(itemView) {
            override fun onBind(message: IMessage?) {
                super.onBind(message)
                message?.createdAt?.let {
                    time.text = DateFormatter.format(it, ("hh:mm a"))
                }
            }
        }

        class CustomOutcomingMessageViewHolder(itemView: View?) :
            MessagesListAdapter.OutcomingMessageViewHolder<IMessage>(itemView) {
            override fun onBind(message: IMessage?) {
                super.onBind(message)
                message?.createdAt?.let {
                    time.text = DateFormatter.format(it, ("hh:mm a"))
                }
            }
        }


        val holdersConfig = MessageHolders()
            .setOutcomingTextConfig(
                CustomOutcomingMessageViewHolder::class.java,
                R.layout.item_custom_outcoming_text_message
            )
            .setIncomingTextConfig(
                CustomIncomingMessageViewHolder::class.java,
                R.layout.item_custom_incoming_text_message
            )


        messagesAdapter =
            MessagesListAdapter(senderId, holdersConfig, imageLoader)
        messagesAdapter?.setLoadMoreListener(this)
        messagesAdapter?.setDateHeadersFormatter { date ->
            if (DateFormatter.isToday(date)) {
                "Today "
            } else if (DateFormatter.isYesterday(date)) {
                "Yesterday"
            } else {
                DateFormatter.format(
                    date,
                    ("dd/MM/yyyy")
                )
            }
        }
        binding.messagesList.setAdapter(messagesAdapter)

        binding.input.inputEditText.filters =
            arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
                if (source.isNotEmpty()) {
                    if (Character.isWhitespace(source[start]) && dest.isEmpty()) {
                        return@InputFilter ""
                    }
                }
                null
            }
            )

    }

    override fun onLoading() {

    }

    override fun onSuccess(message: String) {
    }

    override fun onFailure(message: String) {
    }

}