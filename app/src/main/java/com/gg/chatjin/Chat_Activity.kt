package com.gg.chatjin

import adapters.Chat_Adapter
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dtos.ChatDto

import kotlinx.android.synthetic.main.activity_chat_.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Chat_Activity : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    var boarduid: String = String()
    var chatlist: ArrayList<ChatDto> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_)
        boarduid = intent.getStringExtra("board_uid").toString()
        messageload()
        val list = db.collection("list").document(boarduid).collection("message")
        list.addSnapshotListener { value, error ->
            messageload()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        send()

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun send() {
        val chatRef = db.collection("list").document(boarduid).collection("message")

        chat_start.setOnClickListener {
            val docRef = db.collection("users").document(user!!.uid)   // 나를 찾고
            docRef.get().addOnSuccessListener {
                val uid = it.getField<String>("uid").toString()
                val message = findViewById<EditText>(R.id.chat_message)
                val username = it.getField<String>("username").toString()
                val profile = it.getField<Boolean>("userprofile")
                var createdate = System.currentTimeMillis().toInt()
                var t1=LocalDateTime.now()
                val t2 = DateTimeFormatter.ofPattern("MM-dd일  HH : mm")
                val time = t1.format(t2)
                Log.d("시간확인",""+time)
                val chatDto = ChatDto(uid, username, message.text.toString(), profile!!, createdate, 1,time)

                chatRef.add(chatDto)
                message.hint = ""
                message.text = null
            }
        }
    }

    fun messageload() {
        val list = db.collection("list").document(boarduid).collection("message")
        Log.d("메시지 로드확인",""+boarduid)
        list.orderBy("createdate").get().addOnSuccessListener {
            chatlist = it.toObjects<ChatDto>() as ArrayList<ChatDto>
//            Log.d(
//                "챗리스트 확인", "" + chatlist[0].username + chatlist[0].createdate + chatlist[0].message
//            )

            val adapter = Chat_Adapter(chatlist, LayoutInflater.from(this), user!!.uid)
            chat_re.adapter = adapter
            chat_re.layoutManager = LinearLayoutManager(this)
        }
    }


}