package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg.chatjin.R
import de.hdodenhof.circleimageview.CircleImageView
import dtos.ChatDto

class Friend_Adapter(
    val itemList: ArrayList<ChatDto>,
    val inflater: LayoutInflater
) : RecyclerView.Adapter<Friend_Adapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView
        val img: CircleImageView
        val status:TextView

        init {
            name = itemView.findViewById<TextView>(R.id.fr_name)
            img = itemView.findViewById<CircleImageView>(R.id.fr_img)
            status= itemView.findViewById<TextView>(R.id.fr_status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Friend_Adapter.ViewHolder {
        val view = inflater.inflate(R.layout.friend_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(itemList.get(position).username)
        if (itemList.get(position).profile) {
            var img = itemList.get(position).uid
            var imgload =
                "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
            Glide.with(holder.itemView.context)
                .load(imgload)
                .into(holder.img)
        }
        holder.status.setText(itemList.get(position).status)


    }
}
