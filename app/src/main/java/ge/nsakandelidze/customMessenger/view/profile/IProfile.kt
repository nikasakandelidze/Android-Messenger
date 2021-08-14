package ge.nsakandelidze.customMessenger.view.profile

interface IProfile {
    fun updateUserFields(nickname: String, profession: String)
    fun showMessage(message: String)
    fun redirectToView(viewName: String)
    fun showImage(byteArray: ByteArray)
}