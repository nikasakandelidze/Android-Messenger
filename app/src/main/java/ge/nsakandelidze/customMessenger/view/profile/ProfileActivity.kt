package ge.nsakandelidze.customMessenger.view.profile

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.profile.ProfilePresenter

class ProfileActivity : AppCompatActivity(), IProfile {
    private lateinit var nickNameEditText: TextInputEditText
    private lateinit var professionEditText: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button

    private lateinit var profilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        initState()
    }

    private fun initState() {
        initViewComponents()
        initListeners()
        this.profilePresenter = ProfilePresenter(this)
    }

    private fun initViewComponents() {
        nickNameEditText = findViewById(R.id.nickname)
        professionEditText = findViewById(R.id.profession)
        updateButton = findViewById(R.id.update_button)
        signOutButton = findViewById(R.id.sign_out_button)
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
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    override fun redirectToView(viewName: String) {
        // redirect to antoher activity
    }

}