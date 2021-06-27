package fragments

import adapters.List_Adapter
import adapters.OneAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.gg.chatjin.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.fragment_room_list.*


class roomList : Fragment() {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = user?.uid.toString()

        val userRef = db.collection("users").document(uid).collection("userlist")
        var userlist: ArrayList<OneBoardDto> =ArrayList()
        userRef.get().addOnSuccessListener {
            Log.d("게시글답장확인",""+it)
            val list = it.toObjects<OneBoardDto>()
            for (i in 0 until list.size){
                userlist.add(list[i])
            }
            val adapter = List_Adapter(userlist, LayoutInflater.from(this.context))
            rommlist_re.adapter=adapter
            rommlist_re.layoutManager= LinearLayoutManager(this.context)


        }


    }

}