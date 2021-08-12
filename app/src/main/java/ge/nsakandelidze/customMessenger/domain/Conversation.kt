package ge.nsakandelidze.customMessenger.domain

class Conversation {
    var from_student_id: String? = null
    var to_student_id: String? = null
    var messages: Map<String,Message>? = null
    private var id: String? = null

    constructor()

    constructor(
        from_student_id: String? = null,
        to_student_id: String? = null,
        messages: Map<String,Message>? = null
    ) {
        this.from_student_id = from_student_id;
        this.to_student_id = to_student_id;
        this.messages = messages;
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String? {
        return this.id
    }
}