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
import ge.nsakandelidze.customMessenger.view.signIn.SignInActivity
import ge.nsakandelidze.customMessenger.view.signUp.SignUpActivity

class ProfilePage : Fragment(R.layout.activity_profile), IProfile {
    private lateinit var nickNameEditText: TextInputEditText
    private lateinit var professionEditText: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button

    private lateinit var profilePresenter: ProfilePresenter

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

    override fun updateUserFields(nickname: String, profession: String) {
        nickNameEditText.setText(nickname)
        professionEditText.setText(profession)
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun redirectToView(viewName: String) {
        // redirect to antoher activity
        val intent = Intent(activity, SignInActivity::class.java)
        startActivity(intent)
    }
}