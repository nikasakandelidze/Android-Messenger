package ge.nsakandelidze.customMessenger.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import ge.nsakandelidze.customMessenger.R

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setupVisualElements()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        //below line is temporary
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private fun setupVisualElements() {
        actionBar?.hide()
        supportActionBar?.hide()
    }
}