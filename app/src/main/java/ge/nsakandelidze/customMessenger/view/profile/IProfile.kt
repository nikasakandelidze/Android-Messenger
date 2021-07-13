package ge.nsakandelidze.customMessenger.view.profile

import ge.nsakandelidze.customMessenger.domain.User

interface IProfile {
    fun updateUserFields(nickname: String, profession: String)
    fun showMessage(message: String)
}