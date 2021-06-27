package com.gg.chatjin

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import dtos.ChatDto
import dtos.GroupBoardDto
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.activity_create_room_.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CreateRoom_Activity : AppCompatActivity() {

    val db = Firebase.firestore
    val user = Firebase.auth.currentUser
    var room: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_room_)

        toggle_one.setOnClickListener { room = "oneBoard" }
        toggle_group.setOnClickListener { room = "groupBoard" }

        create_room.setOnClickListener {
            val title = title_room.text.toString()
            if (room != null && title != "") {
                val uid = user?.uid.toString()
                if (room == "oneBoard") {
                    val docRef = db.collection("users").document(uid)
                    docRef.get().addOnSuccessListener {
                        val username = it.getField<String>("username")
                        val profile: Boolean? = it.getField<Boolean>("userprofile")
                        Log.d("유저확인", "" + username + title)
                        val boardid = db.collection(room!!).document()
                        val id = boardid.id
                        val list = db.collection("list").document(id).collection("message")
                        val oneBoardDto = OneBoardDto(id,uid, username!!, title, profile!!,1)
                        boardid.set(oneBoardDto)
                        list.add(oneBoardDto)
                        list_create(room!!,id)
                        finish()
                    }
                }

                if (room == "groupBoard") {
                    val docRef = db.collection("users").document(uid)
                    docRef.get().addOnSuccessListener {
                        val username = it.getField<String>("username")
                        val profile: Boolean? = it.getField<Boolean>("userprofile")
                        Log.d("유저확인", "" + username + title)
                        val boardid = db.collection(room!!).document()
                        val id = boardid.id
                        val list = db.collection("list").document(id).collection("message")
                        var groupBoardDto=GroupBoardDto(id,uid,username!!,title,profile!!,1)
                        boardid.set(groupBoardDto)
                        list.add(groupBoardDto)
                        Log.d("보드아이디 확인",""+boardid)
                        list_create(room!!,id)
                        finish()
                    }
                }
            } else {
                Toast.makeText(this, "선택 또는 방제목을 입력하지 않았습니다", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
