package ge.nsakandelidze.customMessenger.presenter.homepage

import ge.nsakandelidze.customMessenger.storage.ConversationStorage
import ge.nsakandelidze.customMessenger.storage.ImagesStorage
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto
import ge.nsakandelidze.customMessenger.view.homepage.IHomePageView
import java.time.LocalDateTime
import java.util.*

class HomePagePresenter(val view: IHomePageView) {

    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()
    private val conversationStorage: ConversationStorage = ConversationStorage.getInstance()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val imageStorage: ImagesStorage = ImagesStorage.getInstance()

    fun fetchConversationForCurrentUser(filterInput: String) {
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
                    val lastConvo = conv.messages?.values?.sortedBy { it.date }?.last()
                    userDataStorage.getUserDataWithIdOf(idOfAnotherUser) { user ->
                        if (user.nickname?.lowercase(Locale.getDefault())?.contains(
                                filterInput.lowercase(
                                    Locale.getDefault()
                                )
                            ) == true
                        ) {
                            view.AddNewConversationAndupdateConversationsList(
                                ConversationDto(
                                    user.nickname.orEmpty(),
                                    "",
                                    lastConvo?.content.orEmpty(),
                                    lastConvo?.date.orEmpty(),
                                    idOfAnotherUser
                                ), ls.size, counter++
                            )
                        }
                    }
                }
            }
        }
    }


    fun getImageForUser(
        userId: String,
        failConsumer: (Unit) -> Unit,
        byteArrayConsumer: (ByteArray) -> Unit
    ) {
        imageStorage.getImageForUserId(userId, {
            byteArrayConsumer(it)
        }, {
            failConsumer(Unit)
        })
    }
}