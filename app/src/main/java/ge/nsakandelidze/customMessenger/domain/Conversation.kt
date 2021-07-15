package ge.nsakandelidze.customMessenger.domain

class Conversation {
    val from_student_id: String? = null
    val to_student_id: String? = null
    val messages: MutableList<Message>? = null

    constructor()

    constructor(
        from_student_id: String? = null,
        to_student_id: String? = null,
        messages: MutableList<Message>? = null
    ) {
    }
}