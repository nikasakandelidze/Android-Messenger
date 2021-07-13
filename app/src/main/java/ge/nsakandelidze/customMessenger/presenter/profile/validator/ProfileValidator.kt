package ge.nsakandelidze.customMessenger.presenter.profile.validator

class ProfileValidator {
    fun validateInput(nickname: String, profession: String): Boolean {
        return nickname.isNotEmpty() && nickname.isNotBlank() && profession.isNotBlank() && profession.isNotEmpty()
    }
}