package ge.nsakandelidze.customMessenger.view.signUp

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.signUp.SignUpPresenter
import ge.nsakandelidze.customMessenger.view.viewpager.MainPageContainerActivity

class SignUpActivity : AppCompatActivity(), ISignUpView {
    private lateinit var presenter: SignUpPresenter
    private lateinit var usernameComponent: TextInputEditText
    private lateinit var passwordComponent: TextInputEditText
    private lateinit var professionComponent: TextInputEditText
    private lateinit var progressBarLoder: ProgressBar
    private lateinit var signUpButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initializeState()
        initializeListeners()
    }

    private fun initializeState() {
        presenter = SignUpPresenter(this)
        usernameComponent = findViewById(R.id.username)
        passwordComponent = findViewById(R.id.password)
        professionComponent = findViewById(R.id.what_i_do)
        signUpButton = findViewById<Button>(R.id.sign_up_button)
        progressBarLoder = findViewById(R.id.loader_progress_bar)
    }

    private fun initializeListeners() {
        initializeSignUpButtonListener()
    }

    private fun initializeSignUpButtonListener() {
        signUpButton.setOnClickListener {
            val username: String = usernameComponent.text.toString()
            val password: String = passwordComponent.text.toString()
            val profession: String = professionComponent.text.toString()
            progressBarLoder.visibility = View.VISIBLE
            presenter.signUpNewUser(username, password, profession, {
                progressBarLoder.visibility = View.GONE
                val intent = Intent(this, MainPageContainerActivity::class.java)
                startActivity(intent)
            }, {
                Toast.makeText(this, "Failed to sign up", Toast.LENGTH_LONG).show()
                progressBarLoder.visibility = View.GONE
            })
        }
    }

    override fun showFailedSignInMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}