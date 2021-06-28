package com.gg.chatjin

import adapters.PagerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home_activity.*

class Home_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var uid = intent.getStringExtra("uid").toString()

        setContentView(R.layout.activity_home_activity)
        tab_layout.addTab(tab_layout.newTab().setText("친구"))
        tab_layout.addTab(tab_layout.newTab().setText("방리스트"))
        tab_layout.addTab(tab_layout.newTab().setText("1대1채팅"))
        tab_layout.addTab(tab_layout.newTab().setText("그룹채팅"))
        val pagerAdapter = PagerAdapter(supportFragmentManager,5)
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
            val intent = Intent(this,Option_Activity::class.java)
            startActivity(intent)
        }
        floatingActionButton.setOnClickListener {
            val intent = Intent(this,CreateRoom_Activity::class.java)
            startActivity(intent)
        }
    }
}
fun list_create(board: String, boarduid:String) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    if (board == "groupBoard") {
        val groupstore =
            db.collection("groupBoard").document(boarduid).get() // 보드 유아디로 보드 내용검색
        groupstore.addOnSuccessListener {
            val data = it.data
            if (data != null) {
                db.collection("users").document(user!!.uid).collection("userlist").document(boarduid).set(data)
            }  // 보드내용을 내 유저데이터에 저장
        }
    }
    if (board == "oneBoard") {
        val boardstore =
            db.collection("oneBoard").document(boarduid).get() // 보드 유아디로 보드 내용검색
        boardstore.addOnSuccessListener {
            val data = it.data
            if (data != null) {
                db.collection("users").document(user!!.uid).collection("userlist").document(boarduid).set(data)
            }  // 보드내용을 내 유저데이터에 저장
        }
    }
}
fun list_delete(name:String,boardid:String){
    val db = Firebase.firestore
    db.collection(name).document(boardid).delete()

}
fun userlist_delete(uid:String,boardid:String){
    val db = Firebase.firestore
    db.collection("users").document(uid).collection("userlist").document(boardid).delete()
}