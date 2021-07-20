package ge.nsakandelidze.customMessenger.view.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.view.profile.HomePage
import ge.nsakandelidze.customMessenger.view.profile.ProfilePage


class MainPageContainerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (supportActionBar != null) {
            this.supportActionBar?.hide()
        }
        setContentView(R.layout.fragments_container_layout)

        val findViewById = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val onNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener {
                val itemId: Int = it.itemId
                var fragment: Fragment? = null
                if (itemId == R.id.home) {
                    fragment = HomePage()
                } else {
                    fragment = ProfilePage()
                }
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
                    .commit()
                true
            }
        findViewById.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomePage())
            .commit()
    }


}