package com.example.chatty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class chatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerVIew : RecyclerView
    private lateinit var messegeBox: EditText
    private lateinit var sentImage:ImageView

    private lateinit var messegeAdapter: MessegeAdapter
    private lateinit var messegeList: ArrayList<Messege>
    private lateinit var mDbRef:DatabaseReference

    var senderRoom:String? = null
    var recieverRoom:String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name  = intent.getStringExtra("name")
        val recieverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        recieverRoom = senderUid + recieverUid
        senderRoom = recieverUid + senderUid


        supportActionBar?.title =name

        chatRecyclerVIew = findViewById(R.id.chatRecyclerView)
        messegeBox = findViewById(R.id.messege_box)
        sentImage = findViewById(R.id.sentImg)
        messegeList=ArrayList()
        messegeAdapter=MessegeAdapter(this,messegeList)

        chatRecyclerVIew.layoutManager = LinearLayoutManager(this)
        chatRecyclerVIew.adapter = messegeAdapter

        //add the messeges to the recycler view
        mDbRef.child("chats").child(senderRoom!!).child("messeges")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messegeList.clear()
                    for(postSnapshot in snapshot.children){
                        val messege = postSnapshot.getValue(Messege::class.java)
                        messegeList.add(messege!!)
                    }
                    messegeAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        //add the messege to the database
        sentImage.setOnClickListener{
            val messege = messegeBox.text.toString()
            val messegeObject= Messege(messege,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messeges").push()
                .setValue(messegeObject).addOnSuccessListener {
                    mDbRef.child("chats").child(recieverRoom!!).child("messeges").push()
                        .setValue(messegeObject)
                }
            messegeBox.setText("")
        }

    }
}