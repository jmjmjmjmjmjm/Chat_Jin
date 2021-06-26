package com.gg.chatjin

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import dtos.ChatDto
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.activity_chat_.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Chat_Activity : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    var uid: String = String()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_)
        uid = intent.getStringExtra("board_uid").toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        chat_start.setOnClickListener {
            val docRef = db.collection("users").document(user!!.uid)   // 나를 찾고
            docRef.get().addOnSuccessListener {
                val uid = it.getField<String>("uid").toString()
                val message = findViewById<EditText>(R.id.chat_message)
                val username = it.getField<String>("username").toString()
                val profile = it.getField<Boolean>("userprofile")
                var time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
                Log.d("내정보확인", "" + uid + message.text.toString() + username + profile + time)
                val chatDto = ChatDto(uid, username, message.text.toString(), profile!!, time,1)
                Log.d("챗디티오확인", "" + chatDto)
                val chatRef = db.collection("oneBoard").document(uid)
                chatRef.collection("message").add(chatDto)
                message.hint=""
                message.text=null
            }


        }
    }


}