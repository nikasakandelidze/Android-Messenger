package ge.nsakandelidze.customMessenger.domain

class Message {
    var content: String? = null
    var date: String? = null
    var from: String? = null
    var to: String? = null

    constructor()

    constructor(
        content: String? = null,
        date: String? = null,
        from: String? = null,
        to: String? = null
    ) {
        this.content = content
        this.date = date;
        this.from = from
        this.to = to
    }
}
