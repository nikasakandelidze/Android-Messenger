package ge.nsakandelidze.customMessenger.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.Message
import ge.nsakandelidze.customMessenger.presenter.chat.ChatPresenter
import ge.nsakandelidze.customMessenger.presenter.profile.ProfilePresenter
import ge.nsakandelidze.customMessenger.view.homepage.HomePageListAdapter

class ChatPage : AppCompatActivity(), IChatView {
    private lateinit var chatPresenter: ChatPresenter
    private lateinit var otherUserId: String
    private lateinit var messagesListRecyclerView: RecyclerView
    private lateinit var messageChatText: EditText
    private lateinit var sendButton: Button
    private val messages: MutableList<Message> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        otherUserId = intent.getStringExtra("otherUserId")!!
        initializestate()
        initViewComponents()
        initListeners()
    }

    private fun initListeners() {
        sendButton.setOnClickListener {
            val text = messageChatText.text
            chatPresenter.sendNewMessage(text.toString(), otherUserId)
        }
    }

    private fun initViewComponents() {
        messagesListRecyclerView = findViewById<RecyclerView>(R.id.messages_recycler_view)
        sendButton = findViewById(R.id.send_button)
        messageChatText = findViewById(R.id.message_input_id)
        messagesListRecyclerView.adapter =
            ChatMessagesListAdapter(messages, chatPresenter.getUserId())
        messagesListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initializestate() {
        chatPresenter = ChatPresenter(this)
        chatPresenter.getConversationForUserWithIdOf(otherUserId)
    }

    override fun showConversationDetails(conversation: Conversation) {
        messages.clear()
        messages.addAll(
            conversation.messages?.values!!.filterNotNull().toCollection(mutableListOf())
        )
        messagesListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun showMessageToUser(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}