package ge.nsakandelidze.customMessenger.presenter.profile

import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.storage.ImagesStorage
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.view.profile.IUsersSearch
import java.util.*

class UsersSearchPresenter(val view: IUsersSearch) {
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val imageStorage = ImagesStorage.getInstance()
    fun getAllUsers() {
        userDataStorage.getUsers() {
            view.updateUsersList(it)
        }
    }

    fun getUsersBySearchInput(input: String) {
        userDataStorage.getUsers() {
            val result = it.filter { e -> e!!.nickname?.lowercase(Locale.getDefault())!!.contains(input.lowercase(Locale.getDefault())) }
            view.updateUsersList(result.toMutableList())
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
