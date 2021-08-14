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
        if (idOfUser.isEmpty()) {
            view.notifyUser("You are not logged in")
        } else {
            var counter = 0
            conversationStorage.fetchAllConversationsForUser(idOfUser) { ls ->
                ls.forEach { conv ->
                    val fromStudentId = conv.from_student_id
                    val toStudentId = conv.to_student_id
                    var idOfAnotherUser = ""
                    if (fromStudentId.equals(idOfUser)) {
                        idOfAnotherUser = toStudentId.toString()
                    } else {
                        idOfAnotherUser = fromStudentId.toString()
                    }
                    userDataStorage.getUserDataWithIdOf(idOfAnotherUser) { user ->
                        view.AddNewConversationAndupdateConversationsList(
                            ConversationDto(
                                user.nickname.orEmpty(),
                                "",
                                conv.messages?.values?.first()?.content.orEmpty(),
                                conv.messages?.values?.first()?.date.orEmpty(),
                                idOfAnotherUser
                            ), ls.size, counter++
                        )
                    }
                }
            }
        }
    }
}