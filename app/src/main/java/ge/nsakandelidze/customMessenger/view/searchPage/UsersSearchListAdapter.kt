package ge.nsakandelidze.customMessenger.view.homepage

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.presenter.profile.UsersSearchPresenter
import ge.nsakandelidze.customMessenger.view.chat.ChatPage

class UsersSearchListAdapter(
    val users: MutableList<User?>,
    val presenter: UsersSearchPresenter,
    val progressBar: ProgressBar
) :
    RecyclerView.Adapter<UserItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_user_item, parent, false)
        initializeClickListener(view)
        return UserItem(view)
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

    override fun onBindViewHolder(holder: UserItem, position: Int) {
        val userItem = users[position]
        progressBar.visibility = View.VISIBLE
        userItem?.id?.let {
            presenter.getImageForUser(it, {
                progressBar.visibility = View.GONE
                holder.userImage.setImageResource(R.drawable.avatar_image_placeholder)
            }, { b ->
                if (position == users.size - 1) {
                    progressBar.visibility = View.GONE
                }
                val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size);
                holder.userImage.setImageBitmap(bitmap)
            })
        }
        holder.userName.text = userItem!!.nickname
        holder.userProfession.text = userItem.profession
        holder.otherUserId.text = userItem.id

    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class UserItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userImage = itemView.findViewById<ImageView>(R.id.user_image)
    val userName = itemView.findViewById<TextView>(R.id.user_name)
    val userProfession = itemView.findViewById<TextView>(R.id.user_profession)
    val otherUserId = itemView.findViewById<TextView>(R.id.id_of_another_user)
}