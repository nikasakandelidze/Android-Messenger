package ge.nsakandelidze.customMessenger.view.signUp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.signUp.SignUpPresenter

class SignUpActivity : AppCompatActivity(), ISignUpView {
    private lateinit var presenter: SignUpPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        initPresenter()
        initializeListeners()
    }

    private fun initPresenter() {
        presenter = SignUpPresenter(this)
    }

    private fun initializeListeners() {
        initializeSignUpButtonListener()
    }

    private fun initializeSignUpButtonListener() {
        findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            presenter.signUpNewUser("", "", "")
        }
    }

    override fun showFailedSignInMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
            .show()
    }
}