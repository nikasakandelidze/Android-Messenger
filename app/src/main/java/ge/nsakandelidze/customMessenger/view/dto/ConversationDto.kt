package ge.nsakandelidze.customMessenger.view.dto

import ge.nsakandelidze.customMessenger.domain.Message

class ConversationDto(
    val nickname: String,
    val image: String,
    val lastSentMessage: String,
    val date: String,
) {
}