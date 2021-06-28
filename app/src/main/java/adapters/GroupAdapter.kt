package adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg.chatjin.*
import de.hdodenhof.circleimageview.CircleImageView
import dtos.GroupBoardDto
import java.util.zip.Inflater

class GroupAdapter(
    val itemList: ArrayList<GroupBoardDto>,
    val inflater: LayoutInflater,
    val uid: String
) : RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView
        val person: TextView
        val img: CircleImageView
        val delete: Button

        init {
            title = itemView.findViewById(R.id.group_title)
            img = itemView.findViewById(R.id.group_img)
            person = itemView.findViewById(R.id.group_person)
            delete = itemView.findViewById(R.id.group_delete)
            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(itemView.context)
                val ad = builder.create()
                val log: LayoutInflater = LayoutInflater.from(itemView.context)
                val dialogView = log.inflate(R.layout.chat_dialog, null)
                val intent = Intent(itemView.context, Chat_Activity::class.java)
                val no = dialogView.findViewById<Button>(R.id.dialog_no)
                val yes = dialogView.findViewById<Button>(R.id.dialog_yes)
                no.setOnClickListener { ad.dismiss() }
                yes.setOnClickListener {
                    intent.putExtra("board_uid", itemList[position].boardid)
                    itemView.context.startActivity(intent)
                    personup(itemList[position].boardid)
                    list_create("groupBoard", itemList[position].boardid)
                    ad.dismiss()
                }
                ad.setView(dialogView)
                ad.show()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.group_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(itemList.get(position).message)
        holder.person.setText(itemList.get(position).person.toString())
        if (itemList.get(position).profile) {
            val img = itemList.get(position).uid
            val imgload =
                "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
            Glide.with(holder.itemView.context)
                .load(imgload)
                .into(holder.img)
        }
        if (itemList.get(position).uid == uid) {
            holder.delete.visibility = (View.VISIBLE)
            holder.delete.setOnClickListener {
                list_delete("groupBoard", itemList.get(position).boardid)
            }
        }
    }
}