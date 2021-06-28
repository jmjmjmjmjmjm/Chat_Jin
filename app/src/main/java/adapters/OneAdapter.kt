package adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg.chatjin.*
import com.google.firebase.firestore.ktx.getField
import de.hdodenhof.circleimageview.CircleImageView
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.oneonone_item.view.*

class OneAdapter(
    private val itemList: ArrayList<OneBoardDto>,
    private val inflater: LayoutInflater,
    val uid: String
) : RecyclerView.Adapter<OneAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val username: TextView
        val img: CircleImageView
        val delete: Button
        val person: TextView

        init {
            title = itemView.findViewById(R.id.one_title)
            username = itemView.findViewById(R.id.one_username)
            img = itemView.findViewById(R.id.one_profile)
            delete = itemView.findViewById(R.id.one_delete)
            person = itemView.findViewById(R.id.one_person)
            itemView.setOnClickListener {
                if (itemList[position].person < 2) {
                    val builder = AlertDialog.Builder(itemView.context)
                    val ad = builder.create()
                    val log: LayoutInflater = LayoutInflater.from(itemView.context)
                    val dialogView = log.inflate(R.layout.chat_dialog, null)
                    var intent = Intent(itemView.context, Chat_Activity::class.java)

                    var no = dialogView.findViewById<Button>(R.id.dialog_no)
                    var yes = dialogView.findViewById<Button>(R.id.dialog_yes)
                    no.setOnClickListener { ad.dismiss() }
                    yes.setOnClickListener {
                        intent.putExtra("board_uid", itemList[position].boardid)
                        itemView.context.startActivity(intent)
                        personup(itemList[position].boardid)
                        list_create("oneBoard", itemList[position].boardid)
                        ad.dismiss()
                    }
                    ad.setView(dialogView)
                    ad.show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.oneonone_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(itemList.get(position).message)
        holder.username.setText(itemList.get(position).username)
        holder.person.setText((itemList.get(position).person).toString() + "/2")
        if (itemList.get(position).profile) {
            var img = itemList.get(position).uid
            var imgload =
                "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
            Glide.with(holder.itemView.context)
                .load(imgload)
                .into(holder.img)
        }
        if (itemList.get(position).uid == uid) {
            holder.delete.visibility = (View.VISIBLE)
            holder.delete.setOnClickListener {
                list_delete("oneBoard", itemList.get(position).boardid)
            }

        }

    }
}