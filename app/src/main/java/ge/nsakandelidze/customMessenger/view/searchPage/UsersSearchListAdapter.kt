package ge.nsakandelidze.customMessenger.view.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.User

class UsersSearchListAdapter(val users: List<User>) :
    RecyclerView.Adapter<UserItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItem {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_user_item, parent, false)
        return UserItem(view)
    }

    override fun onBindViewHolder(holder: UserItem, position: Int) {
        val userItem = users[position]
        holder.userImage.setImageResource(R.drawable.avatar_image_placeholder)
        holder.userName.text = userItem.nickname
        holder.userProfession.text = userItem.profession
    }

    override fun getItemCount(): Int {
        return users.size
    }
}

class UserItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val userImage = itemView.findViewById<ImageView>(R.id.user_image)
    val userName = itemView.findViewById<TextView>(R.id.user_name)
    val userProfession = itemView.findViewById<TextView>(R.id.user_profession)
}