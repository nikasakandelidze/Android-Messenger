package ge.nsakandelidze.customMessenger.view.searchPage

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.presenter.homepage.HomePagePresenter
import ge.nsakandelidze.customMessenger.presenter.profile.UsersSearchPresenter
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto
import ge.nsakandelidze.customMessenger.view.homepage.HomePageListAdapter
import ge.nsakandelidze.customMessenger.view.homepage.UsersSearchListAdapter

class UsersSearchActivity : AppCompatActivity() {

    private lateinit var usersSearchListRecyclerView: RecyclerView
    private lateinit var presenter: UsersSearchPresenter
    private var usersList : MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initViewComponents()
        initState()
    }

    private fun initViewComponents(){
        usersSearchListRecyclerView = findViewById<RecyclerView>(R.id.users_list)
        usersSearchListRecyclerView.adapter = UsersSearchListAdapter(usersList)
        usersSearchListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initState() {
        this.presenter = UsersSearchPresenter()
        this.presenter.getAllUsers()
    }
}