package ge.nsakandelidze.customMessenger.view.homepage

import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.homepage.HomePagePresenter
import ge.nsakandelidze.customMessenger.view.chat.ChatPage
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto

class HomePageListAdapter(
    val conversations: List<ConversationDto>,
    val homePagePresenter: HomePagePresenter,
    val progressBar: ProgressBar
) :
    RecyclerView.Adapter<ConversationItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItem {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_page_conversation_item, parent, false)
        initializeClickListener(view)
        return ConversationItem(view)
    }

    override fun onBindViewHolder(holder: ConversationItem, position: Int) {
        val conversationItem = conversations[position]
        progressBar.visibility = View.VISIBLE
        homePagePresenter.getImageForUser( conversationItem.idOfAnotherUser,{
            progressBar.visibility = View.GONE
        }, {
            val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size);
            holder.conversationImage.setImageBitmap(bitmap)
            progressBar.visibility = View.GONE
        })
        holder.conversationPersonName.text = conversationItem.nickname
        holder.lastMessageOfConversation.text = conversationItem.lastSentMessage
        holder.timeOfConversation.text = conversationItem.date
        holder.idOfAnotherUser.text = conversationItem.idOfAnotherUser
    }

    override fun getItemCount(): Int {
        return conversations.size
    }

    private fun initializeClickListener(view: View) {
        view.setOnClickListener {
            val chatIntent = Intent(view.context, ChatPage::class.java).putExtra(
                "otherUserId",
                view.findViewById<TextView>(R.id.id_of_another_user).text
            )
            view.context.startActivity(chatIntent)
        }
    }
}

class ConversationItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val conversationImage = itemView.findViewById<ImageView>(R.id.conversation_friend_image)
    val conversationPersonName = itemView.findViewById<TextView>(R.id.name_of_conversation_person)
    val lastMessageOfConversation =
        itemView.findViewById<TextView>(R.id.last_message_of_conversation)
    val timeOfConversation = itemView.findViewById<TextView>(R.id.time_of_message_sent)
    val idOfAnotherUser = itemView.findViewById<TextView>(R.id.id_of_another_user)
}