package ge.nsakandelidze.customMessenger.view.homepage

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.homepage.HomePagePresenter
import ge.nsakandelidze.customMessenger.view.chat.ChatPage
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter.*
import java.time.temporal.ChronoUnit


class HomePageListAdapter(
    private val conversations: List<ConversationDto>,
    private val homePagePresenter: HomePagePresenter,
    private val progressBar: ProgressBar
) :
    RecyclerView.Adapter<ConversationItem>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConversationItem {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_page_conversation_item, parent, false)
        initializeClickListener(view)
        return ConversationItem(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ConversationItem, position: Int) {
        progressBar.visibility = View.VISIBLE
        val conversationItem = conversations[position]
        homePagePresenter.getImageForUser( conversationItem.idOfAnotherUser,{
            holder.conversationImage.setImageResource(R.drawable.avatar_image_placeholder)
            if(position == conversations.size-1){
                progressBar.visibility = View.GONE
            }
        }, {
            if(it.isNotEmpty()){
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size);
                holder.conversationImage.setImageBitmap(bitmap)
            }
            if(position == conversations.size-1){
                progressBar.visibility = View.GONE
            }
        })
        holder.conversationPersonName.text = conversationItem.nickname
        holder.lastMessageOfConversation.text = conversationItem.lastSentMessage
        holder.timeOfConversation.text = this.dateFormatter(conversationItem.date)
        holder.idOfAnotherUser.text = conversationItem.idOfAnotherUser
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun dateFormatter(messageDate: String): String {

        val now = LocalDateTime.now()
        val past = LocalDateTime.parse(messageDate, ISO_LOCAL_DATE_TIME)


        var tempDateTime = LocalDateTime.from(past)

        val years = tempDateTime.until(now, ChronoUnit.YEARS)
        tempDateTime = tempDateTime.plusYears(years)

        val months = tempDateTime.until(now, ChronoUnit.MONTHS)
        tempDateTime = tempDateTime.plusMonths(months)

        val days = tempDateTime.until(now, ChronoUnit.DAYS)
        tempDateTime = tempDateTime.plusDays(days)


        val hours = tempDateTime.until(now, ChronoUnit.HOURS)
        tempDateTime = tempDateTime.plusHours(hours)

        val minutes = tempDateTime.until(now, ChronoUnit.MINUTES)
        tempDateTime = tempDateTime.plusMinutes(minutes)

        val seconds = tempDateTime.until(now, ChronoUnit.SECONDS)

        val month = past.month.toString().substring(0,3)
        val day = past.dayOfMonth.toString()

        return when {
            years == 0L && months == 0L && days == 0L && hours == 0L && minutes in 0..59 -> {
                "$minutes minutes ago"
            }
            years == 0L && months == 0L && days == 0L && hours in 0..24 -> {
                "$hours hours ago"
            }
            else -> "$day $month"
        }
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