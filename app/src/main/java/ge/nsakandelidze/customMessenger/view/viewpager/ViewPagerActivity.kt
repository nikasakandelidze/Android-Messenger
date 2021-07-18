package ge.nsakandelidze.customMessenger.view.viewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.view.profile.HomePage
import ge.nsakandelidze.customMessenger.view.profile.ProfilePage


class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(supportActionBar!=null){
            this.supportActionBar?.hide()
        }
        setContentView(R.layout.view_pager)
        val info : List<Fragment> = listOf(HomePage(), ProfilePage())

        val adapter = ViewPagerAdapter(info, this)


        findViewById<ViewPager2>(R.id.pager).adapter = adapter

        TabLayoutMediator(findViewById<TabLayout>(R.id.tab), findViewById<ViewPager2>(R.id.pager)){
                tab, position ->
            if(position ==0) {
                tab.setIcon(R.drawable.home)
            }else{
                tab.setIcon(R.drawable.settings)
            }

        }.attach()

        findViewById<TabLayout>(R.id.tab)
    }
}