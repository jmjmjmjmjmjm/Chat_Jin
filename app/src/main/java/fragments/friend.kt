package fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gg.chatjin.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_frind.*
import kotlinx.android.synthetic.main.friend_item.*
import kotlinx.android.synthetic.main.profile_update.*


class friend() : Fragment() {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser
    val storage = Firebase.storage
    var update_img: CircleImageView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_frind, container, false)
    }



    fun myprofile() {
        val mname = view?.findViewById<TextView>(R.id.myname)
        val mstatus = view?.findViewById<TextView>(R.id.mystatus)
        val mimg = view?.findViewById<CircleImageView>(R.id.myimg)
        val docRef = db.collection("users").document(user!!.uid)       // 파이어베이스 DB에 username 을 가져오기
        docRef.get().addOnSuccessListener {             // 데이터에서 불러오기
            Log.d("유저객체 확인1", "" + it.getField("uid"))
            var username = it.getField<String>("username")
            var status = it.getField<String>("status")
            mstatus?.text = status
            var img = it.getField<String>("uid")
            var imgload =
                "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
            mname?.text = username
            if (it.getField<Boolean>("userprofile") == true) {
                context?.let {
                    if (mimg != null) {
                        Glide.with(it)
                            .load(imgload)
                            .into(mimg)
                    }
                }
            }
        }

        mimg?.setOnClickListener {      //  프로필업데이트
            val dialog = AlertDialog.Builder(context)
            val di = dialog.create()
            val log = LayoutInflater.from(context)
            val dView = log.inflate(R.layout.profile_update, null)
            update_img = dView.findViewById<CircleImageView>(R.id.update_img)
            val update_name = dView.findViewById<EditText>(R.id.update_name)
            val update_message = dView.findViewById<EditText>(R.id.update_message)
            val update_start = dView.findViewById<Button>(R.id.update_start)
            update_img?.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(intent, 1001)
            }   // 이미지 클릭시
            update_start.setOnClickListener {
                val washingtonRef = db.collection("users").document(user!!.uid)
                washingtonRef.update("username",update_name.text.toString())
                washingtonRef.update("status",update_message.text.toString())
                di.dismiss()
            }   // 업데이트 완료버튼
            di.setView(dView)
            di.show()
        }   //프로필업데이트
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {

                var currentImgUrl: Uri? = data?.data
                val storageRef = storage.reference.child(user!!.uid).child("profile")
                val washingtonRef = db.collection("users").document(user!!.uid)
                storageRef.putFile(currentImgUrl!!).addOnSuccessListener { Log.d("사진수정","완료") }
                washingtonRef.update("profile",true)
                 var bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, currentImgUrl);
                update_img?.setImageBitmap(bitmap)
            }
        } else {
            Log.d("이미지 추가실패", "이미지 추가실패")
        }
    }       // 프로필이미지 업데이트시

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myprofile()
    }


}