package com.gg.chatjin

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import dtos.ChatDto
import kotlinx.android.synthetic.main.activity_chat_.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Chat_Activity : AppCompatActivity() {

    val user = Firebase.auth.currentUser
    val db = Firebase.firestore
    var uid:String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_)
        uid = intent.getStringExtra("board_uid")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStart() {
        super.onStart()
        chat_start.setOnClickListener {
            val docRef = db.collection("users").document(user!!.uid)

            docRef.get().addOnSuccessListener{
                val username =it.getField<String>("username").toString()
                val message = findViewById<EditText>(R.id.chat_message).text.toString()
                val profile = it.getField<Boolean>("userprofile")

            }
        }
    }


}