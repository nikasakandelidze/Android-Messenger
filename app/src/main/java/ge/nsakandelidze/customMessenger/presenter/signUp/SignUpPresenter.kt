package ge.nsakandelidze.customMessenger.presenter.signUp

import android.util.Log
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage
import ge.nsakandelidze.customMessenger.view.signUp.ISignUpView

class SignUpPresenter(val view: ISignUpView) {

    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val validator: SignUpValidator = SignUpValidator()
    private val userStateStorage: UserStateStorage = UserStateStorage.getInstance()

    fun signUpNewUser(
        username: String,
        password: String,
        profession: String,
        sucessCallback: (Unit) -> Unit,
        failCallback: (Unit) -> Unit
    ) {
        val inputParametersValid = validator.isInputParametersValid(username, password, profession)
        Log.d("user", username)
        Log.d("pass", password)
        if (inputParametersValid) {
            userDataStorage.addUser(username, password, profession, {
                userStateStorage.signIn(it.nickname.orEmpty())
                sucessCallback(Unit)
            }, {
                failCallback(Unit)
            })
        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
        }
    }

}