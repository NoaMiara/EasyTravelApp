package com.app.easy.travel.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.easy.travel.databinding.ActivityRegisterBinding
import com.app.easy.travel.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USERS

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnRegister.setOnClickListener {
            createPreferences()
        }


    }


    private fun createPreferences() {
        when {
            binding.etTextFirstName.text?.isEmpty() == true -> Toast.makeText(
                this,
                "Please write your first name",
                Toast.LENGTH_SHORT
            ).show()
            binding.etTextLastName.text?.isEmpty() == true -> Toast.makeText(
                this,
                "Please write your last name",
                Toast.LENGTH_SHORT
            ).show()
            binding.etTextEmail.text?.isEmpty() == true -> Toast.makeText(
                this,
                "Please write your email",
                Toast.LENGTH_SHORT
            ).show()

            binding.etTextPassword.text?.isEmpty() == true -> Toast.makeText(
                this,
                "Please write your password",
                Toast.LENGTH_SHORT
            ).show()
            else -> {

                checkEmail(
                    User(
                        binding.etTextEmail.text.toString(),
                        binding.etTextFirstName.text.toString(),
                        binding.etTextLastName.text.toString(),
                        binding.etTextPassword.text.toString()

                    )
                )
            }

        }

    }

    private fun checkEmail(user: User) {
        FirebaseDatabase.getInstance().reference
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = user.email.replace(".","")
                    if (snapshot.child(USERS).child(email).exists()) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "error this email exists",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        saveInRepository(user)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                }


            })
    }


    private fun saveInRepository(user: User) {

        var dataBase = FirebaseDatabase.getInstance().reference.child(USERS)

        var email = user.email.replace(".","")

        dataBase.child(email).child(USER)
            .setValue(user).addOnSuccessListener {
                Log.d("saveUserOnFireBase", "Success")
                finishRegister()

            }.addOnFailureListener {
                Log.d("saveUserOnFireBase", "Failure")

            }


    }

    private fun finishRegister() {
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }


}