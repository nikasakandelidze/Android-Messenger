package ge.nsakandelidze.customMessenger.domain

data class Conversation(
    val from_student_id: Int? = null,
    val to_student_id: Int? = null,
    val messages: MutableList<Message>,
    val name: String,
    val lastSendMessage: String,
    val sentDate: String
) {

}