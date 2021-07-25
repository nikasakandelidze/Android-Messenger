package ge.nsakandelidze.customMessenger.presenter.profile

import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.profile.IUsersSearch

class UsersSearchPresenter(val view: IUsersSearch) {
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()

    fun getAllUsers() {
        userDataStorage.getUsers() {
            view.updateUsersList(it)
        }
    }
}
