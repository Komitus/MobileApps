package com.example.lab5a

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab5a.DataBase.GameScore

class RecycleAdapter() : RecyclerView.Adapter<RecycleAdapter.MyViewHolder>(){

    private var gamesList = emptyList<GameScore>()
    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val winner_txt: TextView
        val scoreBottom_txt: TextView
        val scoreTop_txt: TextView
        init {
            winner_txt = itemView.findViewById(R.id.textView10)
            scoreBottom_txt = itemView.findViewById(R.id.textView12)
            scoreTop_txt = itemView.findViewById(R.id.textView11)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false))

    }

    override fun getItemCount(): Int {
       return gamesList.size
    }

    override fun onBindViewHolder(holder: RecycleAdapter.MyViewHolder, position: Int) {
        val currentItem = gamesList[position]
        holder.winner_txt.text = currentItem.winner.toString()
        holder.scoreBottom_txt.text = currentItem.player_bottom.toString()
        holder.scoreTop_txt.text = currentItem.player_top.toString()

    }

    fun setData(gameScore: List<GameScore>){
        gamesList = gameScore
        notifyDataSetChanged()
    }




}
