package com.example.zakamal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.zakamal.BottomNav.AkunFragment
import com.example.zakamal.BottomNav.HomeFragment
import com.example.zakamal.BottomNav.MonitoringFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentHome = HomeFragment()
        val fragmentMonitoring = MonitoringFragment()
        val fragmentAkun = AkunFragment()

        setFragment(fragmentHome)

//        Bottom Tab
        findViewById<ImageView>(R.id.iv_menu1).setOnClickListener {
            setFragment(fragmentHome);
            changeImage(findViewById(R.id.iv_menu1), R.drawable.ic_home_active);
            changeImage(findViewById(R.id.iv_menu2), R.drawable.ic_monitoring_disable);
            changeImage(findViewById(R.id.iv_menu3), R.drawable.ic_profile_disable);

//            change text color active and unactive
            findViewById<TextView>(R.id.txt_menu1).setTextColor(resources.getColor(R.color.Primary_Blue_80));
            findViewById<TextView>(R.id.txt_menu2).setTextColor(resources.getColor(R.color.txt_disable_tab));
            findViewById<TextView>(R.id.txt_menu3).setTextColor(resources.getColor(R.color.txt_disable_tab));
        }

        findViewById<ImageView>(R.id.iv_menu2).setOnClickListener {
            setFragment(fragmentMonitoring);
            changeImage(findViewById(R.id.iv_menu1), R.drawable.ic_home_disable);
            changeImage(findViewById(R.id.iv_menu2), R.drawable.ic_monitorign_active);
            changeImage(findViewById(R.id.iv_menu3), R.drawable.ic_profile_disable);

            findViewById<TextView>(R.id.txt_menu1).setTextColor(resources.getColor(R.color.txt_disable_tab));
            findViewById<TextView>(R.id.txt_menu2).setTextColor(resources.getColor(R.color.Primary_Blue_80));
            findViewById<TextView>(R.id.txt_menu3).setTextColor(resources.getColor(R.color.txt_disable_tab));
        }

        findViewById<ImageView>(R.id.iv_menu3).setOnClickListener {
            setFragment(fragmentAkun);
            changeImage(findViewById(R.id.iv_menu1), R.drawable.ic_home_disable);
            changeImage(findViewById(R.id.iv_menu2), R.drawable.ic_monitoring_disable);
            changeImage(findViewById(R.id.iv_menu3), R.drawable.ic_profile_active);

            findViewById<TextView>(R.id.txt_menu1).setTextColor(resources.getColor(R.color.txt_disable_tab));
            findViewById<TextView>(R.id.txt_menu2).setTextColor(resources.getColor(R.color.txt_disable_tab));
            findViewById<TextView>(R.id.txt_menu3).setTextColor(resources.getColor(R.color.Primary_Blue_80));
        }
    }


    //    load fragment
    private fun setFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmantTransaction = fragmentManager.beginTransaction()
        fragmantTransaction.replace(R.id.layout_frame, fragment)
        fragmantTransaction.commit()
    }

    //    membuat fun merubah gambar
    private fun changeImage(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }
}