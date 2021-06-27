package fragments

import adapters.GroupAdapter
import adapters.OneAdapter
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gg.chatjin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dtos.GroupBoardDto
import dtos.OneBoardDto


class group : Fragment() {
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
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val re = view.findViewById<RecyclerView>(R.id.group_re)
        val docRef = db.collection("groupBoard")
        var groupBoardlist: ArrayList<GroupBoardDto> =ArrayList()
        docRef.get().addOnSuccessListener {
            val list = it.toObjects<GroupBoardDto>()
            for (i in 0 until list.size) {
                val boardid = list[i].boardid
                var uid = list[i].uid
                var username =list[i].username
                var title = list[i].message
                var profile = list[i].profile
                var person = list[i].person
                groupBoardlist.add(GroupBoardDto(boardid,uid,username,title,profile,person))
                Log.d("리스트", "" + groupBoardlist)
            }
            Log.d("컨텍스트", "" + ct + re)
            val adapter = GroupAdapter(groupBoardlist, LayoutInflater.from(ct))
            re?.adapter=adapter
            re?.layoutManager= LinearLayoutManager(ct)
        }
    }

}