package ge.nsakandelidze.customMessenger.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.profile.ProfilePresenter
import ge.nsakandelidze.customMessenger.view.signUp.SignUpActivity


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
class ProfilePage : Fragment(R.layout.activity_profile), IProfile {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nickNameEditText: TextInputEditText
    private lateinit var professionEditText: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button

    private lateinit var profilePresenter: ProfilePresenter

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
        val view: View = inflater.inflate(R.layout.activity_profile, container, false)
        initState(view)
        return view
    }

    private fun initState(view: View) {
        initViewComponents(view)
        initListeners()
        this.profilePresenter = ProfilePresenter(this)
        this.profilePresenter.getUserData()
    }

    private fun initViewComponents(view: View) {
        nickNameEditText = view.findViewById(R.id.nickname)
        professionEditText = view.findViewById(R.id.profession)
        updateButton = view.findViewById(R.id.update_button)
        signOutButton = view.findViewById(R.id.sign_out_button)
    }

    private fun initListeners() {
        updateButton.setOnClickListener {
            val nickname: String = nickNameEditText.text.toString()
            val profession: String = professionEditText.text.toString()
            profilePresenter.updateUserData(nickname, profession)
        }
        signOutButton.setOnClickListener {
            profilePresenter.signOut()
        }
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
            ProfilePage().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun updateUserFields(nickname: String, profession: String) {
        nickNameEditText.setText(nickname)
        professionEditText.setText(profession)
    }

    override fun showMessage(message: String) {
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun redirectToView(viewName: String) {
        // redirect to antoher activity
        val intent = Intent(activity, SignUpActivity::class.java)
        startActivity(intent)
    }
}