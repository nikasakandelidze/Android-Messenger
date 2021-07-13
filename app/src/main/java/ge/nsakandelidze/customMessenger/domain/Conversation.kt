package ge.nsakandelidze.customMessenger.domain

data class Conversation(
    val photo: String,
    val name: String,
    val lastSendMessage: String,
    val sentDate: String
) {

}