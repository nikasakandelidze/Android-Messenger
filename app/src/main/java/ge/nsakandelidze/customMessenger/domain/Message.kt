package ge.nsakandelidze.customMessenger.domain

data class Message(
    val content: String? = null,
    val date: String? = null,
    val from: Int? = null,
    val to: Int? = null
)
