package ge.nsakandelidze.customMessenger.domain

class Conversation {
    val from_student_id: Long? = null
    val to_student_id: Long? = null
    val messages: MutableList<Message>? = null

    constructor()

    constructor(
        from_student_id: Long? = null,
        to_student_id: Long? = null,
        messages: MutableList<Message>? = null
    ) {
    }
}