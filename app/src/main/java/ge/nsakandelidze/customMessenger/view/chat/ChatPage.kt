package ge.nsakandelidze.customMessenger.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.Message
import ge.nsakandelidze.customMessenger.presenter.chat.ChatPresenter
import ge.nsakandelidze.customMessenger.view.homepage.HomePageListAdapter

class ChatPage : AppCompatActivity(), IChatView {
    private lateinit var chatPresenter: ChatPresenter
    private lateinit var otherUserId: String
    private lateinit var messagesListRecyclerView: RecyclerView
    private val messages: MutableList<Message> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        otherUserId = intent.getStringExtra("otherUserId")!!
        initViewComponents()
        initializestate()
    }

    private fun initViewComponents() {
        messagesListRecyclerView = findViewById<RecyclerView>(R.id.messages_recycler_view)
        messagesListRecyclerView.adapter =
            ChatMessagesListAdapter(messages)
        messagesListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initializestate() {
        chatPresenter = ChatPresenter(this)
        chatPresenter.getConversationForUserWithIdOf(otherUserId)
    }

    override fun showConversationDetails(conversation: Conversation) {
        messages.clear()
        messages.addAll(conversation.messages!!.filterNotNull().toCollection(mutableListOf()))
        messagesListRecyclerView.adapter?.notifyDataSetChanged()
    }
}