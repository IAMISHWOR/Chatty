package com.example.chatty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class LogIn : AppCompatActivity() {
    private lateinit var email:EditText
    private lateinit var password:EditText
    private lateinit var login:Button
    private lateinit var signup:Button

    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        email = findViewById(R.id.eml_edit)
        password = findViewById(R.id.pass_edit)
        login = findViewById(R.id.login_btn)
        signup = findViewById(R.id.signup_btn)

        signup.setOnClickListener{
            val intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            logIn(email,password);
        }
    }
    private fun logIn(email:String,pass:String){
        //logic for login
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn,MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LogIn,"User does not exist",Toast.LENGTH_SHORT).show()
                }
            }
    }
}