package fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gg.chatjin.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_frind.*


class friend(val uid: String) : Fragment() {
    val db = Firebase.firestore
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

    override fun onStart() {
        super.onStart()
        myprofile()

    }

    fun myprofile() {
        val mname = view?.findViewById<TextView>(R.id.myname)
        val mstatus = view?.findViewById<TextView>(R.id.mystatus)
        val mimg = view?.findViewById<CircleImageView>(R.id.myimg)
        val docRef = db.collection("users").document(uid)       // 파이어베이스 DB에 username 을 가져오기

        docRef.get().addOnSuccessListener {             // 데이터에서 불러오기
            Log.d("유저객체 확인1", "" + it.getField("uid"))
            var username = it.getField<String>("username")
            var status = it.getField<String>("status")
            mstatus?.text = status
            var img = it.getField<String>("uid")
            mname?.text = username
            if (mimg != null) {
                context?.let {
                    Glide.with(it)
                        .load("https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token=")
                        .into(mimg)
                }
            }
        }
    }

}