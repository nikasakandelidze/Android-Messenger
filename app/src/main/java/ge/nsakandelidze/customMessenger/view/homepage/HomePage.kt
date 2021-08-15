package ge.nsakandelidze.customMessenger.view.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.homepage.HomePagePresenter
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto
import ge.nsakandelidze.customMessenger.view.homepage.HomePageListAdapter
import ge.nsakandelidze.customMessenger.view.homepage.IHomePageView
import java.util.stream.Collector
import java.util.stream.Collectors


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomePage : Fragment(R.layout.home_page_activiy), IHomePageView {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var conversationsListRecyclerView: RecyclerView
    private lateinit var presenter: HomePagePresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var searchBar: TextInputEditText

    private var listOfConversations: MutableList<ConversationDto> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.home_page_activiy, container, false)
        initState()
        initViewComponents(view)
        return view
    }

    private fun initViewComponents(view: View) {
        progressBar = view.findViewById(R.id.loader_progress_bar)
        searchBar = view.findViewById(R.id.username)
        conversationsListRecyclerView = view.findViewById<RecyclerView>(R.id.home_page_listing)
        conversationsListRecyclerView.adapter =
            HomePageListAdapter(listOfConversations, presenter, progressBar)
        conversationsListRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        searchBar.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                presenter.fetchConversationForCurrentUser("")
            } else {
                presenter.fetchConversationForCurrentUser(text.toString())
            }
        }
    }

    private fun initState() {
        this.presenter = HomePagePresenter(this)
        this.presenter.fetchConversationForCurrentUser("")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun notifyUser(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun updateConversationsList(conversations: List<ConversationDto>) {
        listOfConversations.clear()
        listOfConversations.addAll(conversations)
        conversationsListRecyclerView.adapter?.notifyDataSetChanged()
    }

    override fun AddNewConversationAndupdateConversationsList(
        conversation: ConversationDto,
        length: Int,
        counter: Int
    ) {
        if (counter == 0) {
            listOfConversations.clear()
            conversationsListRecyclerView.adapter?.notifyDataSetChanged()
        }
        listOfConversations.add(conversation)
        conversationsListRecyclerView.adapter?.notifyItemInserted(listOfConversations.size - 1)
    }
}