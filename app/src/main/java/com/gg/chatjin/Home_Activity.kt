package com.gg.chatjin

import adapters.PagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_home_activity.*
import kotlinx.android.synthetic.main.fragment_frind.*

class Home_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uid = intent.getStringExtra("uid").toString()

        setContentView(R.layout.activity_home_activity)
        tab_layout.addTab(tab_layout.newTab().setText("친구"))
        tab_layout.addTab(tab_layout.newTab().setText("방리스트"))
        tab_layout.addTab(tab_layout.newTab().setText("1대1채팅"))
        tab_layout.addTab(tab_layout.newTab().setText("그룹채팅"))
        val pagerAdapter = PagerAdapter(supportFragmentManager,5, uid!!)
        view_pager.adapter=pagerAdapter
        tab_layout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                view_pager.currentItem=tab!!.position
            }
        })
        view_pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
    }

    override fun onStart() {
        super.onStart()

        option.setOnClickListener {
            val intent = Intent(this,OptionActivity::class.java)
            startActivity(intent)
        }
    }
}