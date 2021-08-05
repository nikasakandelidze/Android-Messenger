package ge.nsakandelidze.customMessenger.view.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Message

class ChatMessagesListAdapter(private val messages: MutableList<Message>) :
    RecyclerView.Adapter<MessageItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItem {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.sent_message_item, parent, false)
        return MessageItem(view)
    }

    override fun onBindViewHolder(holder: MessageItem, position: Int) {
        val messageItem = messages[position]
        holder.messageContent.text = messageItem.content
        holder.messageDate.text = messageItem.date
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}

class MessageItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val messageContent = itemView.findViewById<TextView>(R.id.message_text)
    val messageDate = itemView.findViewById<TextView>(R.id.message_time)
}