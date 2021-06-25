package com.gg.chatjin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.activity_create_room_.*

class CreateRoom_Activity : AppCompatActivity() {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser
    var room: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room_)

        toggle_one.setOnClickListener { room = "oneBoard" }
        toggle_group.setOnClickListener { room = "groupBoard" }

        create_room.setOnClickListener {
            val title = title_room.text.toString()
            if (room != null && title != "") {
                val uid = user?.uid.toString()
                val docRef = db.collection("users").document(uid)
                docRef.get().addOnSuccessListener {
                    val username = it.getField<String>("username")
                    val profile: Boolean? = it.getField<Boolean>("userprofile")
                    Log.d("유저확인", "" + username + title)
                    var oneBoardDto = OneBoardDto(uid, username!!, title,profile!!)
                    db.collection(room!!).document(uid).set(oneBoardDto)
                    finish()
                }
            } else {
                Toast.makeText(this, "선택 또는 방제목을 입력하지 않았습니다", Toast.LENGTH_SHORT).show()
            }
        }

    }
}