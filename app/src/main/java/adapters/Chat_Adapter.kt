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
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg.chatjin.*
import de.hdodenhof.circleimageview.CircleImageView
import dtos.ChatDto
import dtos.OneBoardDto

class Chat_Adapter(
    private val itemList: ArrayList<ChatDto>,
    private val inflater: LayoutInflater,
    var my: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class YouViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val youchat_name: TextView = itemView.findViewById(R.id.youchat_name)
        val youchat_content: TextView = itemView.findViewById(R.id.youchat_content)
        val youchat_date: TextView? = itemView.findViewById(R.id.youchat_date)

        fun bind(item: ChatDto) {
            youchat_content.text = item.message
            youchat_date?.text = item.time.toString()
            youchat_name.text = item.username
            youchat_content.setOnClickListener {
                val builder = AlertDialog.Builder(itemView.context)
                val ad = builder.create()
                val log: LayoutInflater = LayoutInflater.from(itemView.context)
                val dialogView = log.inflate(R.layout.chat_click_dialog, null)

                val cl_name = dialogView.findViewById<TextView>(R.id.click_name)
                val cl_img = dialogView.findViewById<CircleImageView>(R.id.click_img)
                val cl_plus = dialogView.findViewById<Button>(R.id.click_plus)
                cl_name.text = itemList.get(position).username
                if (itemList.get(position).profile) {
                    var img = itemList.get(position).uid
                    var imgload =
                        "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
                    Glide.with(itemView.context)
                        .load(imgload)
                        .into(cl_img)
                }
                cl_plus.setOnClickListener { freind_add(my,itemList.get(position).uid,itemList.get(position)) }
                ad.setView(dialogView)
                ad.show()
            }
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mychat_name: TextView = itemView.findViewById(R.id.chat_name)
        val mychat_content: TextView = itemView.findViewById(R.id.chat_content)
        val mychat_date: TextView? = itemView.findViewById(R.id.chat_date)

        fun bind(item: ChatDto) {
            mychat_content.text = item.message
            mychat_date?.text = item.time.toString()
            mychat_name.text = item.username
            mychat_name.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                val view = inflater.inflate(R.layout.my_chat, parent, false)
                return MyViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.you_chat, parent, false)
                return YouViewHolder(view)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemList[position].uid == my) 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            (holder as MyViewHolder).bind(itemList[position])
            holder.setIsRecyclable(false)
        } else {
            (holder as YouViewHolder).bind(itemList[position])
            holder.setIsRecyclable(false)
        }
    }

}