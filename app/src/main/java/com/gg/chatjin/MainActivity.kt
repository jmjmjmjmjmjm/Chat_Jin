package com.gg.chatjin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.gg.chatjin.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var auth = Firebase.auth
    val db = Firebase.firestore

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp()
        join.setOnClickListener {
            val intent = Intent(this, Join_Activity::class.java)
            startActivity(intent)
        }
        start_login.setOnClickListener { login() }

        auth.addAuthStateListener {
            val user = it.currentUser
            val docRef = db.collection("users").document(user?.uid.toString())
            docRef.get().addOnSuccessListener { document ->
                Log.d("도큐먼트", "" + document.data)
                if (document.data != null && user?.uid != null) {
                    finish()
                    val intent = Intent(this, Home_Activity::class.java)
                    intent.putExtra("uid", user?.uid)
                    startActivity(intent)
                }
                if (document.data != null && user?.uid == null) {
                    finish()
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }

            }
        }


    }

    fun sp() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.emailList,
            R.layout.support_simple_spinner_dropdown_item
        )
        binding.spinner.adapter = adapter
        spinner.setSelection(1)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                email_login.setText("이메일을 선택하세요! ->")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    1 -> {
                        email_login.setText("naver.com")
                    }
                    2 -> {
                        email_login.setText("nate.com")
                    }
                    3 -> {
                        email_login.setText("gmail.com")
                    }
                }
            }

        }
    }

    fun login() {
        val email = id_login.text.toString() + "@" + email_login.text.toString()
        val password = password_login.text.toString()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("로그인확인", "signInWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("uid", user?.uid)
                    intent.putExtra("email", user?.email)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("로그인실패", "signInWithEmail:failure", task.exception)

                }
            }
    }

}
