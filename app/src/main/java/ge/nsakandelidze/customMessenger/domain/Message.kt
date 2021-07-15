package ge.nsakandelidze.customMessenger.domain

class Message {
    var content: String? = null
    var date: String? = null
    var from: Long? = null
    var to: Long? = null

    constructor()

    constructor(
        content: String? = null,
        date: String? = null,
        from: Long? = null,
        to: Long? = null
    ) {
        this.content = content
        this.date = date;
        this.from = from
        this.to = to
    }
}
