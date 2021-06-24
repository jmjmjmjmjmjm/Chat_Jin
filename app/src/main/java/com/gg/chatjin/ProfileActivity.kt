package com.gg.chatjin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dtos.UserDto
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        createuser.setOnClickListener {
            create()
            finish()
            start()
        }
    }

    fun create() {
        val uid = intent.getStringExtra("uid").toString()
        val email = intent.getStringExtra("email").toString()
        val username = nickname.text.toString()
        val role = "user"
        val status = ""
        val userDto = UserDto(uid, role, username, email, status, null)
        db.collection("users").document(uid).set(userDto)
    }
    fun start(){
        val intent = Intent(this, Home_Activity::class.java)
        startActivity(intent)
    }

}