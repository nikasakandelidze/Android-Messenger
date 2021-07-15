package ge.nsakandelidze.customMessenger.presenter.signIn

import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.signIn.ISignIn

class SignInPresenter(val view: ISignIn) {

    private val validator: SignInValidator = SignInValidator()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()

    fun signInUser(
        username: String,
        password: String,
        successCallback: (Unit) -> Unit,
        failCallback: (Unit) -> Unit
    ) {
        val inputParametersValid = validator.isInputParametersValid(username, password)
        if (inputParametersValid) {
            userDataStorage.checkIfUserExist(username, password, {
                userStateStorage.signIn(it.nickname.orEmpty())
                successCallback(Unit)
            }, failCallback)
        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
        }
    }
}