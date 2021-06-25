package com.gg.chatjin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_option.*

class Option_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)
        listener()

    }

    private fun listener() {
        val user = Firebase.auth.currentUser
        password_ch.setOnClickListener {
            val intent = Intent(this, com.gg.chatjin.password_ch::class.java)
            startActivity(intent)
            finish()
        }
        blacklist.setOnClickListener { }
        user_out.setOnClickListener {
            val db = Firebase.firestore
            if (user != null) {
                db.collection("users").document(user.uid).delete()
            }
            user?.delete()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Firebase.auth.signOut()
                    moveTaskToBack(true)
                    finish()
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        user_logout.setOnClickListener {
            moveTaskToBack(true)
            finishAffinity()
            Firebase.auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}