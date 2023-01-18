package com.example.chatty

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth


class MessegeAdapter(val context: Context,val messegeList:ArrayList<Messege>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECIEVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 1){
            //inflate recieve
            val view:View = LayoutInflater.from(context).inflate(R.layout.recieve,parent,false)
            return recieveViewHolder(view)

        }
        else{
            //inflate sent
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return sentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messegeList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessege  = messegeList[position]


        if (holder.javaClass == sentViewHolder::class.java){


            //do the stuff for sent messege
            val viewHolder = holder as sentViewHolder


            holder.sentMessege.text = currentMessege.messege
        }
        else{
            //do the stuff for recieving messege
            val viewHolder = holder as recieveViewHolder
            holder.recieveMessege.text = currentMessege.messege
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessege = messegeList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessege.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECIEVE
        }
    }
    class sentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val sentMessege = itemView.findViewById<TextView>(R.id.txt_sent_messege)

    }
    class recieveViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val recieveMessege = itemView.findViewById<TextView>(R.id.txt_recieve_messege)
    }
}