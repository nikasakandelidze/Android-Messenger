package ge.nsakandelidze.customMessenger.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.Message
import ge.nsakandelidze.customMessenger.presenter.chat.ChatPresenter

class ChatPage : AppCompatActivity(), IChatView {
    private lateinit var chatPresenter: ChatPresenter
    private lateinit var otherUserId: String
    private lateinit var messagesListRecyclerView: RecyclerView
    private lateinit var messageChatText: EditText
    private lateinit var sendButton: Button
    private lateinit var userPicture: ImageView
    private lateinit var userName: TextView
    private lateinit var userProfession: TextView

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
        messagesListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initializeState() {
        chatPresenter = ChatPresenter(this)
        chatPresenter.getConversationForUserWithIdOf(otherUserId)
    }

    override fun showConversationDetails(conversation: Conversation?) {
        messages.clear()
        if(conversation?.messages != null){
            messages.addAll(
                conversation.messages?.values!!.filterNotNull().toCollection(mutableListOf())
            )
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