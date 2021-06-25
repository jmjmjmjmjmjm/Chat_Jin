package com.gg.chatjin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_password_ch.*

class password_ch : AppCompatActivity() {
    val user = Firebase.auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_ch)
        val newpwd = findViewById<TextView>(R.id.newpassword)
        password_chbtn.setOnClickListener {
            if (newpassword.text.toString()==newpassword2.text.toString()){
                user!!.updatePassword(newpwd.text.toString())
                    .addOnCompleteListener {
                        Log.d("패스워드 변경완료","변경됨")
                        finishAffinity()
                        Firebase.auth.signOut()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                    }
            }
        }
    }
}