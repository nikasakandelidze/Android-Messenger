package ge.nsakandelidze.customMessenger.presenter.profile

import ge.nsakandelidze.customMessenger.presenter.profile.validator.ProfileValidator
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage

class UsersSearchPresenter() {
    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()

    fun getAllUsers(){
        userDataStorage.getUsers {
            
        }
    }
}