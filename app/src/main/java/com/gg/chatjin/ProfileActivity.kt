package com.gg.chatjin

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import dtos.UserDto
import kotlinx.android.synthetic.main.activity_profile.*
import java.io.File

class ProfileActivity : AppCompatActivity() {
    val db = Firebase.firestore
    var uid:String?=null
    val storage = Firebase.storage
    var userprofile:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uid =intent.getStringExtra("uid")
        setContentView(R.layout.activity_profile)
        img_plus.setOnClickListener { img() }
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
        val userDto = UserDto(uid, role, username, email,userprofile, status, null)
        db.collection("users").document(uid).set(userDto)
    }

    fun start() {
        val intent = Intent(this, Home_Activity::class.java)
        startActivity(intent)
    }

    fun img() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {

                var currentImgUrl: Uri? = data?.data
                val storageRef = storage.reference.child(uid!!).child("profile")
                storageRef.putFile(currentImgUrl!!).addOnSuccessListener { Log.d("사진추가","완료") }
                userprofile=true
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, currentImgUrl)
                img_plus.setImageBitmap(bitmap)
            }
        } else {
            Log.d("이미지 추가실패", "이미지 추가실패")
        }
    }

}