package com.app.easy.travel.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.easy.travel.databinding.ActivityLoginBinding
import com.app.easy.travel.helpers.*
import com.app.easy.travel.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.app.easy.travel.main.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignIn.setOnClickListener {
            checkIfFieldsEmpty()
            checkInRepository()
        }
        binding.registerNow.setOnClickListener {
            Intent(this, RegisterActivity::class.java).apply {
                startActivity(this)
                finish()
            }

        }


    }

    private fun checkIfFieldsEmpty() {
        when {
            binding.etTextLoginEmail.text?.isEmpty() == true -> Toast.makeText(
                this,
                "Please write your email",
                Toast.LENGTH_SHORT
            ).show()

            binding.etTextPassword.text?.isEmpty() == true -> Toast.makeText(
                this,
                "Please write your Password",
                Toast.LENGTH_SHORT
            ).show()


        }
    }

    private fun checkInRepository() {
        FirebaseDatabase.getInstance().reference.addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var email = binding.etTextLoginEmail.text.toString()
                email = email.replace(".", "")

                if (snapshot.child(USERS).child(email).exists()) {

                    val user =
                        snapshot.child(USERS).child(email).child(
                            USER
                        )
                            .getValue(User::class.java)

                    if (user?.password!!.equals(binding.etTextPassword.text.toString())) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Successfully Login",
                            Toast.LENGTH_SHORT
                        ).show()
                        createPreferences(user)

                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "The password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()

                    }


                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        "The email is incorrect",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }


            override fun onCancelled(error: DatabaseError) {

            }


        })
    }

    private fun createPreferences(user: User) {
        val preferences = getSharedPreferences(USER, MODE_PRIVATE)
        preferences.edit().putString(USER_FIRST_NAME, user.firstName).commit()
        preferences.edit().putString(USER_LAST_NAME, user.lastName).commit()
        preferences.edit().putString(USER_EMAIL, user.email).commit()
        preferences.edit().putString(USER_PASSWORD, user.password).commit()
        preferences.edit().putBoolean(FIRST_TIME, false).commit()
        finishLogin()


    }

    private fun finishLogin() {
        Intent(this, HomeActivity::class.java).apply {
            startActivity(this)
            finish()

        }
    }


}