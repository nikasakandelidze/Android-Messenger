package ge.nsakandelidze.customMessenger.presenter.signIn

class SignInValidator {

    fun isInputParametersValid(
        username: String,
        password: String
    ): Boolean {
        return isParameterValid(username) && isParameterValid(password)
    }

    private fun isParameterValid(parameter: String): Boolean {
        return parameter.isNotBlank() && parameter.isNotEmpty()
    }
}