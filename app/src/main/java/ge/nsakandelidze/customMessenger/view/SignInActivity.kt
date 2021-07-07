package ge.nsakandelidze.customMessenger.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import ge.nsakandelidze.customMessenger.R

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //below line is temporary
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initializeListeners()
    }

    private fun initializeListeners() {
        initializeSignUpButtonListener()
    }

    private fun initializeSignUpButtonListener() {
        findViewById<Button>(R.id.sign_up_button).setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}