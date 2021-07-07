package ge.nsakandelidze.customMessenger.presenter.signUp

class SignUpValidator {

    fun isInputParametersValid(
        username: String,
        password: String,
        profession: String
    ): Boolean {
        return isParameterValid(username) && isParameterValid(password) && isParameterValid(
            profession
        )
    }

    private fun isParameterValid(parameter: String): Boolean {
        return parameter.isNotBlank() && parameter.isNotEmpty()
    }
}