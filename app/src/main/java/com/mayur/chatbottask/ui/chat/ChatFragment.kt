package com.mayur.chatbottask.ui.chat

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
class ChatFragment : Fragment(), MessageInput.InputListener,
    MessagesListAdapter.OnLoadMoreListener, StateListener {
    val senderId = "45544"

    private lateinit var binding: ActivityChatBinding

    private val viewModel: ChatViewModel by viewModels()
    var messagesAdapter: MessagesListAdapter<ChatMessage>? = null
    private var imageLoader: ImageLoader? = null
    lateinit var meUser: User
    lateinit var youUser: User

    private val args: ChatFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_chat, container, false)

        binding.imgMenu.setOnClickListener {
            findNavController().popBackStack()
        }
        viewModel.botResponse.observe(viewLifecycleOwner, {
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
            args.userId,
            args.userName,
            ""
        )

        youUser = User(
            "63906",
            "ChatBot",
            ""
        )

        initAdapter()

        binding.input.setInputListener { input ->
            sendMessage(input.toString())
            false
        }

        return binding.root
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

        viewModel.getBotReply(textData, meUser.id)
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
            MessagesListAdapter(meUser.id, holdersConfig, imageLoader)
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