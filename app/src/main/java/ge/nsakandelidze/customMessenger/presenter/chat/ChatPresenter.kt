package ge.nsakandelidze.customMessenger.presenter.chat

import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.storage.ConversationStorage
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.chat.IChatView

class ChatPresenter(val chatView: IChatView) {
    private val conversationStorage = ConversationStorage.getInstance()
    private val userStateStorage = UserStateStorage.getInstance()
    private val userDataStorage = UserDataStorage.getInstance()

    fun getConversationForUserWithIdOf(otherUserId: String) {
        val idOfUser = userStateStorage.getIdOfUser()
        conversationStorage.getConversationDetailsForUsers(idOfUser, otherUserId) {
            chatView.showConversationDetails(it)
        }
    }

    fun sendNewMessage(message: String, otherUserId: String) {
        if (message.isEmpty()) {
            chatView.showMessageToUser("cant send empty message.")
        } else {
            val idOfUser = userStateStorage.getIdOfUser()
            conversationStorage.addNewMessageIntoConversation(idOfUser, otherUserId, message) {
                getConversationForUserWithIdOf(otherUserId)
            }
        }
    }

    fun getUserId(): String{
        return userStateStorage.getIdOfUser()
    }

    fun getFriendInfo(id: String){
        userDataStorage.getUserDataWithIdOf(id){
            chatView.showFriendInfo(it.nickname!!, it.profession!!)
        }
    }

}