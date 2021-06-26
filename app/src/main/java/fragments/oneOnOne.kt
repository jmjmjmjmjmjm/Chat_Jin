package fragments

import adapters.OneAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gg.chatjin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dtos.OneBoardDto
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_one_on_one.*


class oneOnOne : Fragment() {

    val db = Firebase.firestore
    var ct: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ct =container?.context
        return inflater.inflate(R.layout.fragment_one_on_one, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val re = view.findViewById<RecyclerView>(R.id.oneOnOne_re)
        super.onViewCreated(view, savedInstanceState)
        val docRef = db.collection("oneBoard")
        var oneBoardDtolist: ArrayList<OneBoardDto> =ArrayList()
        docRef.get().addOnSuccessListener {
            val list = it.toObjects<OneBoardDto>()
            for (i in 0 until list.size) {
                val boardid = list[i].boardid
                var uid = list[i].uid
                var username =list[i].username
                var title = list[i].title
                var profile = list[i].profile
                oneBoardDtolist.add(OneBoardDto(boardid,uid,username,title,profile))
                Log.d("리스트", "" + oneBoardDtolist)
            }
            Log.d("컨텍스트", "" + ct + re)
            val adapter = OneAdapter(oneBoardDtolist, LayoutInflater.from(ct))
            re?.adapter=adapter
            re?.layoutManager=LinearLayoutManager(ct)
        }
    }
}