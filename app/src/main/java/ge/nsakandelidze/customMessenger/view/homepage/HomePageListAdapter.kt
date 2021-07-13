package ge.nsakandelidze.customMessenger.view.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation

class HomePageListAdapter(val conversations: List<Conversation>) :
    RecyclerView.Adapter<ConversationItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_page_conversation_item, parent, false)
        return ConversationItem(view)
    }

    override fun onBindViewHolder(holder: ConversationItem, position: Int) {
        val conversationItem = conversations[position]
        holder.conversationImage.setImageResource(R.drawable.avatar_image_placeholder)
        holder.conversationPersonName.text = conversationItem.name
        holder.lastMessageOfConversation.text = conversationItem.lastSendMessage
        holder.timeOfConversation.text = conversationItem.sentDate
    }

    override fun getItemCount(): Int {
        return conversations.size
    }
}

class ConversationItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val conversationImage = itemView.findViewById<ImageView>(R.id.conversation_friend_image)
    val conversationPersonName = itemView.findViewById<TextView>(R.id.name_of_conversation_person)
    val lastMessageOfConversation =
        itemView.findViewById<TextView>(R.id.last_message_of_conversation)
    val timeOfConversation = itemView.findViewById<TextView>(R.id.time_of_message_sent)
}