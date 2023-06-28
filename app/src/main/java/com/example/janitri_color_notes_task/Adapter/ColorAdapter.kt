package com.example.janitri_color_notes_task.Adapter


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.janitri_color_notes_task.R
import com.example.janitri_color_notes_task.RoomDB.ColorEntity



class ColorAdapter(private var data: List<ColorEntity> , private val context : Context) : RecyclerView.Adapter<ColorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.color_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.color_code.setText(data.get(position).colorValue)
        holder.date_created.setText(data.get(position).createdAt)
        holder.cardview_ll.setCardBackgroundColor(Color.parseColor(data.get(position).colorValue))


    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun update_data(data1 : List<ColorEntity>){
        data = data1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        var color_code : TextView = itemView.findViewById(R.id.color_code_tv)
        var date_created : TextView = itemView.findViewById(R.id.date_tv);
        var cardview_ll : CardView = itemView.findViewById(R.id.cardview)


    }
}
