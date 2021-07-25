package ge.nsakandelidze.customMessenger.presenter.chat

import ge.nsakandelidze.customMessenger.storage.ConversationStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.chat.IChatView

class ChatPresenter(val chatView: IChatView) {
    private val conversationStorage = ConversationStorage.getInstance()
    private val userStateStorage = UserStateStorage.getInstance()

    fun getConversationForUserWithIdOf(otherUserId: String) {
        val idOfUser = userStateStorage.getIdOfUser()
        conversationStorage.getConversationDetailsForUsers(idOfUser, otherUserId) {
            chatView.showConversationDetails(it)
        }
    }

}