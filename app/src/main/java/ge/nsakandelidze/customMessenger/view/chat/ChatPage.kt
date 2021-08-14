package ge.nsakandelidze.customMessenger.view.chat

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.Message
import ge.nsakandelidze.customMessenger.presenter.chat.ChatPresenter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatPage : AppCompatActivity(), IChatView {
    private lateinit var chatPresenter: ChatPresenter
    private lateinit var otherUserId: String
    private lateinit var messagesListRecyclerView: RecyclerView
    private lateinit var messageChatText: EditText
    private lateinit var sendButton: Button
    private lateinit var userPicture: ImageView
    private lateinit var userName: TextView
    private lateinit var userProfession: TextView
    private lateinit var backButton: ImageView

    private val messages: MutableList<Message> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        otherUserId = intent.getStringExtra("otherUserId")!!
        initializeState()
        initViewComponents()
        chatPresenter.getFriendInfo(otherUserId)
        initListeners()
    }

    private fun initListeners() {
        sendButton.setOnClickListener {
            val text = messageChatText.text
            chatPresenter.sendNewMessage(text.toString(), otherUserId)
            messageChatText.setText("")
            messagesListRecyclerView.scrollToPosition(messages.size - 1)
        }
    }

    private fun initViewComponents() {
        messagesListRecyclerView = findViewById(R.id.messages_recycler_view)

        userName = findViewById(R.id.friend_name)
        userPicture = findViewById(R.id.friend_image)
        userProfession = findViewById(R.id.friend_profession)

        sendButton = findViewById(R.id.send_button)
        messageChatText = findViewById(R.id.message_input_id)
        messagesListRecyclerView.adapter =
            ChatMessagesListAdapter(messages, chatPresenter.getUserId())
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager.stackFromEnd = true
        messagesListRecyclerView.layoutManager =
            linearLayoutManager
        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initializeState() {
        chatPresenter = ChatPresenter(this)
        chatPresenter.getConversationForUserWithIdOf(otherUserId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showConversationDetails(conversation: Conversation?) {
        messages.clear()
        if (conversation?.messages != null) {
            messages.addAll(
                conversation.messages?.values!!.filterNotNull()
                    .toCollection(mutableListOf())
                    .sortedBy {
                        LocalDateTime.parse(
                            it.date,
                            DateTimeFormatter.ISO_LOCAL_DATE_TIME
                        )
                    }
            )
            messagesListRecyclerView.scrollToPosition(messages.size - 1)
            messagesListRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun showMessageToUser(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun showFriendInfo(nickname: String, profession: String) {
        userName.text = nickname
        userProfession.text = profession
        userPicture.setImageResource(R.drawable.avatar_image_placeholder)
    }
}