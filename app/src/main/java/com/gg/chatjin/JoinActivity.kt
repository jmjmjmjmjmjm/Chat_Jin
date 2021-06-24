package com.gg.chatjin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_main.*

class JoinActivity : AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        auth=Firebase.auth

        start_join.setOnClickListener { join() }
    }
    fun join(){
            val email = email_join.text.toString()
            val password = password_join.text.toString()
            val password2= password_join2.text.toString()
            if(password==password2) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("TAG", "createUserWithEmail:success")
                            val user = auth.currentUser
                            Log.d("로그인완료", "로그인확인")
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.

                        }
                    }
            }else{
                Log.d("패스워드가 같지않습니다","패스워드 불일치")
            }
    }
}