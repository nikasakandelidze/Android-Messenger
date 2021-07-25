package ge.nsakandelidze.customMessenger.view.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.presenter.chat.ChatPresenter

class ChatActivity : AppCompatActivity(), IChatView {
    private lateinit var chatPresenter: ChatPresenter
    private lateinit var otherUserId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        otherUserId = intent.getStringExtra("otherUserId")!!
        initializestate()
    }

    private fun initializestate() {
        chatPresenter = ChatPresenter(this)
        chatPresenter.getConversationForUserWithIdOf(otherUserId)
    }

    override fun showConversationDetails(conversation: Conversation) {
        conversation.messages
    }
}