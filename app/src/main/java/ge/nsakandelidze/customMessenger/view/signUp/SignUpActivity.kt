package ge.nsakandelidze.customMessenger.view.signUp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.signUp.SignUpPresenter
import ge.nsakandelidze.customMessenger.view.homepage.HomePageActivity

class SignUpActivity : AppCompatActivity(), ISignUpView {
    private lateinit var presenter: SignUpPresenter
    private lateinit var usernameComponent: TextInputEditText
    private lateinit var passwordComponent: TextInputEditText
    private lateinit var professionComponent: TextInputEditText
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
    }

    private fun initializeListeners() {
        initializeSignUpButtonListener()
    }

    private fun initializeSignUpButtonListener() {
        signUpButton.setOnClickListener {
            val username: String = usernameComponent.text.toString()
            val password: String = passwordComponent.text.toString()
            val profession: String = professionComponent.text.toString()
            presenter.signUpNewUser(username, password, profession)
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun showFailedSignInMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}