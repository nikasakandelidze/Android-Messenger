package ge.nsakandelidze.customMessenger.view.signIn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.signIn.SignInPresenter
import ge.nsakandelidze.customMessenger.presenter.signUp.SignUpPresenter
import ge.nsakandelidze.customMessenger.view.signUp.ISignUpView
import ge.nsakandelidze.customMessenger.view.signUp.SignUpActivity

class SignInActivity : AppCompatActivity(), ISignIn {

    private lateinit var presenter: SignInPresenter
    private lateinit var usernameComponent: TextInputEditText
    private lateinit var passwordComponent: TextInputEditText
    private lateinit var signInButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //below line is temporary
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initializeState()
        initializeListeners()
    }

    private fun initializeListeners() {
        initializeSignInButtonListener()
    }

    private fun initializeState() {
        presenter = SignInPresenter(this)
        usernameComponent = findViewById(R.id.username)
        passwordComponent = findViewById(R.id.password)
        signInButton = findViewById<Button>(R.id.sign_in_button)
    }

    private fun initializeSignInButtonListener() {
        signInButton.setOnClickListener {
            val username: String = usernameComponent.text.toString()
            val password: String = passwordComponent.text.toString()
            val success = presenter.signInUser(username, password)
//            val intent = Intent(this, SignUpActivity::class.java)
//            startActivity(intent)
        }
    }

    override fun showFailedSignInMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}