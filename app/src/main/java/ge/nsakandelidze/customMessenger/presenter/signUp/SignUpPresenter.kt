package ge.nsakandelidze.customMessenger.presenter.signUp

import ge.nsakandelidze.customMessenger.view.signUp.ISignUpView

class SignUpPresenter(val view: ISignUpView) {

    private val validator: SignUpValidator = SignUpValidator()

    fun signUpNewUser(username: String, password: String, profession: String) {
        val inputParametersValid = validator.isInputParametersValid(username, password, profession)
        if (inputParametersValid) {

        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
        }
    }

}