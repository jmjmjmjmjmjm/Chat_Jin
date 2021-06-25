package fragments

import adapters.OneAdapter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gg.chatjin.R
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import dtos.OneBoardDto


class oneOnOne : Fragment() {
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one_on_one, container, false)
    }

    override fun onStart() {
        super.onStart()
        val docRef = db.collection("oneBoard")
        var oneBoardDtolist:ArrayList<OneBoardDto>
        docRef.get().addOnSuccessListener {
            val list =it.toObjects<OneBoardDto>()
            for (i in 0 until list.size)

            Log.d("리스트",""+list[i].uid)
        }
    }
}