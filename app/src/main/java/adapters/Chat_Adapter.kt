package adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gg.chatjin.R
import de.hdodenhof.circleimageview.CircleImageView
import dtos.ChatDto
import dtos.OneBoardDto

class Chat_Adapter(
    private val itemList: ArrayList<ChatDto>,
    private val inflater: LayoutInflater,
    var my: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class YouViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val youchat_name : TextView = itemView.findViewById(R.id.youchat_name)
        val youchat_content: TextView = itemView.findViewById(R.id.youchat_content)
        val youchat_date: TextView? = itemView.findViewById(R.id.youchat_date)

        fun bind(item: ChatDto) {
            youchat_content.text = item.message
            youchat_date?.text = item.createdate
            youchat_name.text= item.username
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mychat_name : TextView = itemView.findViewById(R.id.chat_name)
        val mychat_content: TextView = itemView.findViewById(R.id.chat_content)
        val mychat_date: TextView? = itemView.findViewById(R.id.chat_date)

        fun bind(item: ChatDto) {
            mychat_content.text = item.message
            mychat_date?.text = item.createdate
            mychat_name.text= item.username
            Log.d("어뎁터 바인드확인",""+item.username)
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
        if (holder is MyViewHolder){
            (holder as MyViewHolder).bind(itemList[position])
            holder.setIsRecyclable(false)
        }else{
            (holder as YouViewHolder).bind(itemList[position])
            holder.setIsRecyclable(false)
        }
    }

}