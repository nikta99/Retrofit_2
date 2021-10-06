package com.example.retrofit2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_layout.view.*

class MyAdapter (private val loginUser: ArrayList<LoginUser>, private val listener :

Listener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(loginUser : LoginUser)

    }


    private val colors : Array<String> = arrayOf("#7E57C2", "#42A5F5")


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.bind(loginUser[position], listener, colors, position)

    }

    override fun getItemCount(): Int = loginUser.count()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)

    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(loginUser: LoginUser, listener: Listener, colors : Array<String>, position: Int) {

            itemView.setOnClickListener{ listener.onItemClick(loginUser) }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 2]))
            itemView.text_name.text = loginUser.login
            itemView.text_price.text = loginUser.type

        }

    }

}
