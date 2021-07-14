package ge.nsakandelidze.customMessenger.presenter.profile

import ge.nsakandelidze.customMessenger.presenter.profile.validator.ProfileValidator
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.profile.IProfile

class ProfilePresenter(val view: IProfile) {
    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val validator: ProfileValidator = ProfileValidator()

    fun updateUserData(nickname: String, profession: String) {
        val validateInput = validator.validateInput(nickname, profession)
        if (!validateInput) {
            view.showMessage("nickname and profession fields cant be empty.")
        } else {
            val userId = userStateStorage.getIdOfUser()
            userDataStorage.updateUserWithIdOf(userId, nickname,profession)
            view.updateUserFields(nickname, profession)
        }
    }

    fun getUserData() {
        val idOfUser = userStateStorage.getIdOfUser()
        userDataStorage.getUserDataWithIdOf("1") {
            view.updateUserFields(it.nickname.orEmpty(), it.profession.orEmpty())
        }
    }

    fun signOut() {
        userStateStorage.signOut()
    }
}