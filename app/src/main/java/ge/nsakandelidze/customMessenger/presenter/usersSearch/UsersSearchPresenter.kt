package ge.nsakandelidze.customMessenger.presenter.profile

import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.storage.ImagesStorage
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.view.profile.IUsersSearch

class UsersSearchPresenter(val view: IUsersSearch) {
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val imageStorage = ImagesStorage.getInstance()
    fun getAllUsers() {
        userDataStorage.getUsers() {
            view.updateUsersList(it)
        }
    }

    fun getUsersBySearchInput(input : String){
        // TODO search for users containing input as prefix
        userDataStorage.getUsers() {
            var result  = filteredUsers(input, it)
            view.updateUsersList(it)
        }
    }

    private fun filteredUsers(input: String, list: MutableList<User?>): MutableList<User?> {
        return mutableListOf()
    }

    fun getImageForUser(userId: String, failConsumer: (Unit) -> Unit, byteArrayConsumer: (ByteArray) -> Unit) {
        imageStorage.getImageForUserId(userId, {
            byteArrayConsumer(it)
        }, {
            failConsumer(Unit)
        })
    }
}
