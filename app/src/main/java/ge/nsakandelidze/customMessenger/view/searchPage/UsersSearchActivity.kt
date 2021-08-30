package ge.nsakandelidze.customMessenger.view.searchPage

import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.presenter.profile.UsersSearchPresenter
import ge.nsakandelidze.customMessenger.view.homepage.UsersSearchListAdapter
import ge.nsakandelidze.customMessenger.view.profile.IUsersSearch
import java.util.*
import kotlin.concurrent.schedule

class UsersSearchActivity : AppCompatActivity(), IUsersSearch {

    private lateinit var usersSearchListRecyclerView: RecyclerView
    private lateinit var presenter: UsersSearchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var searchBar: TextInputEditText
    private lateinit var backButton: ImageView

    private var usersList: MutableList<User?> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initState()
        initViewComponents()
    }

    private val MIN_CHAR_NUMBER = 3

    private fun initViewComponents() {
        backButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            onBackPressed()
        }
        searchBar = findViewById(R.id.username)
        searchBar.doOnTextChanged { text, start, count, after ->
            if (text.isNullOrEmpty()) {
                presenter.getUsersBySearchInput("")
            } else {
                if (text.length >= MIN_CHAR_NUMBER) {
                    Timer("SettingUp", false).schedule(500) {
                        presenter.getUsersBySearchInput(text.toString())
                    }
                }
            }
        }
        usersSearchListRecyclerView = findViewById<RecyclerView>(R.id.users_list)
        progressBar = findViewById(R.id.loader_progress_bar)
        usersSearchListRecyclerView.adapter =
            UsersSearchListAdapter(usersList, presenter, progressBar)
        usersSearchListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun initState() {
        this.presenter = UsersSearchPresenter(this)
        this.presenter.getAllUsers()
    }

    override fun updateUsersList(users: MutableList<User?>) {
        usersList.clear()
        usersList.addAll(users)
        usersSearchListRecyclerView.adapter?.notifyDataSetChanged()
    }
}