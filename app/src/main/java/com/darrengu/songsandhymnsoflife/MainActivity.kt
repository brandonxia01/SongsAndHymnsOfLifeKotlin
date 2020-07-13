package com.darrengu.songsandhymnsoflife

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.darrengu.songsandhymnsoflife.fragment.FragNumber
import com.darrengu.songsandhymnsoflife.fragment.FragSearch
import com.darrengu.songsandhymnsoflife.fragment.FragSongList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragNumber = FragNumber()
        val fragSearch = FragSearch()
        val fragSongList = FragSongList()

        navigation.setOnNavigationItemSelectedListener { item ->
            val fragment: Fragment =
                    when (item.itemId) {
                        R.id.navigation_number -> fragNumber
                        R.id.navigation_search -> fragSearch
                        R.id.navigation_list -> fragSongList
                        else -> fragSongList
                    }
            supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit()
            true
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragNumber)
                .commit()
    }
}
