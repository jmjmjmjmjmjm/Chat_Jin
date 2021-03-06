package fragments

import adapters.Friend_Adapter
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg.chatjin.R
import com.gg.chatjin.freind_add
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.hdodenhof.circleimageview.CircleImageView
import dtos.ChatDto
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_frind.*
import kotlinx.android.synthetic.main.friend_item.*
import kotlinx.android.synthetic.main.profile_update.*


class friend() : Fragment() {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser
    val storage = Firebase.storage
    var update_img: CircleImageView? = null
    var friend_re: RecyclerView? = null
    var ct: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ct = container?.context
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frind, container, false)
    }


    fun myprofile() {
        val mname = view?.findViewById<TextView>(R.id.myname)
        val mstatus = view?.findViewById<TextView>(R.id.mystatus)
        val mimg = view?.findViewById<CircleImageView>(R.id.myimg)
        val docRef = db.collection("users").document(user!!.uid)       // ?????????????????? DB??? username ??? ????????????
        docRef.addSnapshotListener { value, error ->        // ??????????????? ????????????
            Log.d("???????????? ??????1", "" + value?.getField("uid"))
            var username = value?.getField<String>("username")
            var status = value?.getField<String>("status")
            mstatus?.text = status
            var img = value?.getField<String>("uid")
            var imgload =
                "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
            mname?.text = username
            if (value?.getField<Boolean>("userprofile") == true) {
                context?.let {
                    if (mimg != null) {
                        Glide.with(it)
                            .load(imgload)
                            .into(mimg)
                    }
                }
            }
        }



        mimg?.setOnClickListener {      //  ?????????????????????
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
            }   // ????????? ?????????
            update_start.setOnClickListener {
                val washingtonRef = db.collection("users").document(user!!.uid)
                washingtonRef.update("username", update_name.text.toString())
                washingtonRef.update("status", update_message.text.toString())
                di.dismiss()
            }   // ???????????? ????????????
            di.setView(dView)
            di.show()
        }   //?????????????????????
    }

    fun friends() {
        val f = db.collection("freinds").document(user!!.uid).collection(user!!.uid)
        f.addSnapshotListener { value, error ->
            var friendDtolist: ArrayList<ChatDto> = ArrayList()
            if (value != null) {
                var list = value.toObjects<ChatDto>()
                for (i in list.indices) {
                    friendDtolist.add(list[i])
                }
//                Log.d("????????????", "" + friendDtolist[0].status)
                if (friendDtolist.size > 0) {
                    val adapter = Friend_Adapter(friendDtolist, LayoutInflater.from(ct))
                    friend_re = view?.findViewById(R.id.friend_re)
                    friend_re?.adapter = adapter
                    friend_re?.layoutManager = LinearLayoutManager(ct)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) {

                var currentImgUrl: Uri? = data?.data
                val storageRef = storage.reference.child(user!!.uid).child("profile")
                val washingtonRef = db.collection("users").document(user!!.uid)
                storageRef.putFile(currentImgUrl!!).addOnSuccessListener { Log.d("????????????", "??????") }
                washingtonRef.update("profile", true)
                var bitmap =
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, currentImgUrl);
                update_img?.setImageBitmap(bitmap)
            }
        } else {
            Log.d("????????? ????????????", "????????? ????????????")
        }
    }       // ?????????????????? ???????????????

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myprofile()
        friends()
    }

}