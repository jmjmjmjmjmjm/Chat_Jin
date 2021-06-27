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
    var boarduid: String = String()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_)
        boarduid = intent.getStringExtra("board_uid").toString()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()

        val chatRef = db.collection("list").document(boarduid).collection("message")
        chatRef.addSnapshotListener { value, error ->
            Log.d("메시지확인",""+value)
        }

        chat_start.setOnClickListener {
            val docRef = db.collection("users").document(user!!.uid)   // 나를 찾고
            docRef.get().addOnSuccessListener {
                val uid = it.getField<String>("uid").toString()
                val message = findViewById<EditText>(R.id.chat_message)
                val username = it.getField<String>("username").toString()
                val profile = it.getField<Boolean>("userprofile")
                var time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()
                val chatDto = ChatDto(uid, username, message.text.toString(), profile!!, time,1)

                val groupstore = db.collection("groupBoard").document(boarduid).get() // 보드 유아디로 보드 내용검색
                groupstore.addOnSuccessListener {
                    Log.d("보드데이터확인",""+it.data)
                    val data = it.data
                    if (data != null) {
                        db.collection("users").document(user!!.uid).collection("userlist").add(data)
                    }  // 보드내용을 내 유저데이터에 저장
                }

                val boardstore = db.collection("oneBoard").document(boarduid).get() // 보드 유아디로 보드 내용검색
                boardstore.addOnSuccessListener {
                    Log.d("보드데이터확인",""+it.data)
                    val data = it.data
                    if (data != null) {
                        db.collection("users").document(user!!.uid).collection("userlist").add(data)
                    }  // 보드내용을 내 유저데이터에 저장
                }


                chatRef.add(chatDto)
                message.hint=""
                message.text=null
            }
        }


    }




}