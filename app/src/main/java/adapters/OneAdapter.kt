package adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gg.chatjin.R
import com.google.firebase.firestore.ktx.getField
import de.hdodenhof.circleimageview.CircleImageView
import dtos.OneBoardDto
import kotlinx.android.synthetic.main.oneonone_item.view.*

class OneAdapter (
    val itemList:ArrayList<OneBoardDto>,
    val inflater: LayoutInflater
): RecyclerView.Adapter<OneAdapter.ViewHolder>() {
    class ViewHolder (itemView:View) : RecyclerView.ViewHolder(itemView) {
        val title:TextView
        val username:TextView
        val img:CircleImageView
        init {
            title = itemView.findViewById(R.id.one_title)
            username= itemView.findViewById(R.id.one_username)
            img = itemView.findViewById(R.id.one_profile)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val view = inflater.inflate(R.layout.oneonone_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.setText(itemList.get(position).title)
        holder.username.setText(itemList.get(position).username)
        if (itemList.get(position).profile) {
            var img = itemList.get(position).uid
            var imgload = "https://firebasestorage.googleapis.com/v0/b/chatjin-12713.appspot.com/o/$img%2Fprofile?alt=media&token="
            Glide.with(holder.itemView.context)
                .load(imgload)
                .into(holder.img)
        }
    }
}