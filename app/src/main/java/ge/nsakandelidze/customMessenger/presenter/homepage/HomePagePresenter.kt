package ge.nsakandelidze.customMessenger.presenter.homepage

import ge.nsakandelidze.customMessenger.storage.ConversationStorage
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto
import ge.nsakandelidze.customMessenger.view.homepage.IHomePageView

class HomePagePresenter(val view: IHomePageView) {

    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()
    private val conversationStorage: ConversationStorage = ConversationStorage.getInstance()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()

    fun fetchConversationForCurrentUser() {
        val idOfUser = userStateStorage.getIdOfUser()
        if (idOfUser == -1L) {
            view.notifyUser("You are not logged in")
        } else {
            conversationStorage.fetchAllConversationsForUser(idOfUser) { ls ->
                ls.forEach { conv ->
                    val fromStudentId = conv.from_student_id
                    val toStudentId = conv.to_student_id
                    var idOfAnotherUser = ""
                    if (fromStudentId == idOfUser) {
                        idOfAnotherUser = toStudentId.toString()
                    } else {
                        idOfAnotherUser = fromStudentId.toString()
                    }
                    userDataStorage.getUserDataWithIdOf(idOfAnotherUser) { user ->
                        view.AddNewConversationAndupdateConversationsList(
                            ConversationDto(
                                user.nickname.orEmpty(),
                                "",
                                conv.messages?.get(1)?.content.orEmpty(),
                                conv.messages?.get(1)?.date.orEmpty(),
                            )
                        )
                    }
                }
            }
        }
    }
}