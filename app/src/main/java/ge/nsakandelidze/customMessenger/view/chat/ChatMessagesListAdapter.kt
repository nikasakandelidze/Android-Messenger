package ge.nsakandelidze.customMessenger.view.chat

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Message
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class ChatMessagesListAdapter(
    private val messages: MutableList<Message>,
    private val user: String
) :
    RecyclerView.Adapter<ChatMessagesListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when (viewType) {
            TYPE_1 -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.sent_message_item, parent, false)
                VHHeader(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recieved_message_item, parent, false)
                VHItem(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var type = 0
        type = if (messages[position].from.equals(user)) 1
        else 2
        when (type) {
            TYPE_1 -> {
                val header = holder as VHHeader
                messages[holder.layoutPosition].let {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        header.bindView(it)
                    }
                }
            }
            TYPE_2 -> {
                val item = holder as VHItem
                messages[holder.layoutPosition].let {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        item.bindView(it)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        var type = 0
        type = if (messages[position].from.equals(user)) 1
        else 2

        return type
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class VHHeader(itemView: View) : ViewHolder(itemView) {
        fun bindView(message: Message) {
            itemView.findViewById<TextView>(R.id.message_text).text = message.content
            itemView.findViewById<TextView>(R.id.message_time).text = dateFormatter(message.date!!)
        }
    }

    private fun dateFormatter(messageDate: String): String {
        val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.parse(messageDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } else {
            TODO("VERSION.SDK_INT < O")
        }

        val month = date.month.toString().substring(0, 3)
        val day = date.dayOfMonth.toString()
        val time = date.hour.toString() + ":" + date.minute.toString()
        return "$day $month $time"
    }

    inner class VHItem(itemView: View) : ViewHolder(itemView) {
        fun bindView(message: Message) {
            itemView.findViewById<TextView>(R.id.message_text).text = message.content
            itemView.findViewById<TextView>(R.id.message_time).text = dateFormatter(message.date!!)
        }
    }

    companion object {
        const val TYPE_1 = 1
        const val TYPE_2 = 2
    }
}