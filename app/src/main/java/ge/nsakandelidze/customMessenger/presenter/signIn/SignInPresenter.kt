package ge.nsakandelidze.customMessenger.presenter.signIn

import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.view.signIn.ISignIn

class SignInPresenter(val view: ISignIn) {

    private val validator: SignInValidator = SignInValidator()
    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()

    fun signInUser(username: String, password: String): Boolean {
        val inputParametersValid = validator.isInputParametersValid(username, password)
        if (inputParametersValid) {
            return userDataStorage.checkIfUserExist(username, password)
        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
            return false
        }
    }
}