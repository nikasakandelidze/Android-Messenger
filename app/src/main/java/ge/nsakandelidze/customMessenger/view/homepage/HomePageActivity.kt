package ge.nsakandelidze.customMessenger.view.homepage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.domain.Conversation

class HomePageActivity : AppCompatActivity() {
    private lateinit var conversationsListRecyclerView: RecyclerView
    private var listOfConversations: MutableList<Conversation> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_page_activiy)
        initViewComponents()
    }

    private fun initViewComponents() {
        conversationsListRecyclerView = findViewById<RecyclerView>(R.id.home_page_listing)
        conversationsListRecyclerView.adapter = HomePageListAdapter(listOfConversations)
        conversationsListRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }
}