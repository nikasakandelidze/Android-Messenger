package ge.nsakandelidze.customMessenger.view.homepage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.homepage.HomePagePresenter
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto

class HomePageActivity : AppCompatActivity(), IHomePageView {
    private lateinit var conversationsListRecyclerView: RecyclerView
    private lateinit var presenter: HomePagePresenter
    private var listOfConversations: MutableList<ConversationDto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page_activiy)
        initViewComponents()
        initState()
    }

    private fun initViewComponents() {
        conversationsListRecyclerView = findViewById<RecyclerView>(R.id.home_page_listing)
        conversationsListRecyclerView.adapter = HomePageListAdapter(listOfConversations)
        conversationsListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initState() {
        this.presenter = HomePagePresenter(this)
        this.presenter.fetchConversationForCurrentUser()
    }

    override fun notifyUser(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun updateConversationsList(conversations: List<ConversationDto>) {
        runOnUiThread {
            listOfConversations.clear()
            listOfConversations.addAll(conversations)
            conversationsListRecyclerView.adapter?.notifyDataSetChanged()
        }
    }

    override fun AddNewConversationAndupdateConversationsList(conversation: ConversationDto) {
        runOnUiThread {
            listOfConversations.add(conversation)
            conversationsListRecyclerView.adapter?.notifyDataSetChanged()
        }
    }
}