package ge.nsakandelidze.customMessenger.presenter.signUp

import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.view.signUp.ISignUpView

class SignUpPresenter(val view: ISignUpView) {

    private val userDataStorage: UserDataStorage = UserDataStorage.getInstance()
    private val validator: SignUpValidator = SignUpValidator()

    fun signUpNewUser(username: String, password: String, profession: String): Boolean {
        val inputParametersValid = validator.isInputParametersValid(username, password, profession)
        if (inputParametersValid) {
            val success = userDataStorage.addUser(username, password, profession)
            if(! success){
                view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
            }else{
                return true
            }
        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
        }
        return false
    }

}