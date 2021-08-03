package ge.nsakandelidze.customMessenger.domain

class Conversation {
    val from_student_id: String? = null
    val to_student_id: String? = null
    val messages: Map<String,Message>? = null
    private var id: String = ""

    constructor()

    constructor(
        from_student_id: String? = null,
        to_student_id: String? = null,
        messages: MutableList<Message>? = null
    ) {
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String {
        return this.id
    }
}