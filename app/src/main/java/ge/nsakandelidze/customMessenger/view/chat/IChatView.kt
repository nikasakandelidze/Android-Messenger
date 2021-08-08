package ge.nsakandelidze.customMessenger.view.chat

import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.User

interface IChatView {
    fun showConversationDetails(conversation: Conversation)
    fun showMessageToUser(message: String)
}