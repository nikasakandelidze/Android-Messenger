package ge.nsakandelidze.customMessenger.view.homepage

import ge.nsakandelidze.customMessenger.view.dto.ConversationDto

interface IHomePageView {
    fun notifyUser(message: String)
    fun clearRecycleView()
    fun updateConversationsList(conversations: List<ConversationDto>)
    fun AddNewConversationAndupdateConversationsList(conversations: ConversationDto, length: Int, counter: Int)
}