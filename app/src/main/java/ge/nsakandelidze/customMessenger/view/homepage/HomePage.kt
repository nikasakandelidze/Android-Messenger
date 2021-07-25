package ge.nsakandelidze.customMessenger.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.homepage.HomePagePresenter
import ge.nsakandelidze.customMessenger.view.dto.ConversationDto
import ge.nsakandelidze.customMessenger.view.homepage.HomePageListAdapter
import ge.nsakandelidze.customMessenger.view.homepage.IHomePageView


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentWeather.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class HomePage : Fragment(R.layout.home_page_activiy), IHomePageView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var conversationsListRecyclerView: RecyclerView
    private lateinit var presenter: HomePagePresenter
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
        initViewComponents(view)
        initState(view)
        return view
    }

    private fun initViewComponents(view: View) {
        conversationsListRecyclerView = view.findViewById<RecyclerView>(R.id.home_page_listing)
        conversationsListRecyclerView.adapter = HomePageListAdapter(listOfConversations)
        conversationsListRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initState(view: View) {
        this.presenter = HomePagePresenter(this)
        this.presenter.fetchConversationForCurrentUser()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment currentWeather.
         */
        // TODO: Rename and change types and number of parameters
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

    override fun AddNewConversationAndupdateConversationsList(conversation: ConversationDto) {
        listOfConversations.add(conversation)
        conversationsListRecyclerView.adapter?.notifyDataSetChanged()
    }
}