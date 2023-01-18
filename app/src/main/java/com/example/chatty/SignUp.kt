package com.example.chatty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var name:EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var signup: Button

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        name = findViewById(R.id.name_edit)
        email = findViewById(R.id.eml_edit)
        password = findViewById(R.id.pass_edit)
        signup = findViewById(R.id.signup_btn)

        signup.setOnClickListener {
            val name = name.text.toString()
            val email = email.text.toString();
            val password = password.text.toString()

            signUp(name,email,password)
        }
    }
    private fun signUp(name:String,email:String,pass:String){
        //logic for sign up
        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToTheDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp,"some error occured",Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToTheDatabase(name:String,email:String,uid:String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))
    }
}