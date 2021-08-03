package ge.nsakandelidze.customMessenger.view.chat

import ge.nsakandelidze.customMessenger.domain.Conversation

interface IChatView {
    fun showConversationDetails(conversation: Conversation)
    fun showMessageToUser(message: String)
}