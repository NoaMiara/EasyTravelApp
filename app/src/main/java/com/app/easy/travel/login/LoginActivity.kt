package com.app.easy.travel.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.app.easy.travel.databinding.ActivityLoginBinding
import com.app.easy.travel.helpers.FIRST_TIME
import com.app.easy.travel.helpers.USER
import com.app.easy.travel.helpers.USERS
import com.app.easy.travel.helpers.USER_EMAIL
import com.app.easy.travel.helpers.USER_FIRST_NAME
import com.app.easy.travel.helpers.USER_LAST_NAME
import com.app.easy.travel.helpers.USER_PASSWORD
import com.app.easy.travel.main.HomeActivity
import com.app.easy.travel.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout for this activity using View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set onClickListener for Sign In button
        binding.btnSignIn.setOnClickListener {
            checkIfFieldsEmpty()   // Check if fields are empty
            checkInRepository()    // Check login credentials in database
        }

        // Set onClickListener for Register Now button
        binding.registerNow.setOnClickListener {
            // Launch the RegisterActivity and finish the current activity
            Intent(this, RegisterActivity::class.java).apply {
                startActivity(this)
                finish()
            }
        }
    }

    private fun checkIfFieldsEmpty() {
        // Check if email and password fields are empty, show a toast message if any field is empty
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
        // Check if login credentials match in the database
        FirebaseDatabase.getInstance().reference.addListenerForSingleValueEvent(object :
            ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // Get the entered email, replace any "." character in the email address
                var email = binding.etTextLoginEmail.text.toString()
                email = email.replace(".", "")

                // Check if user exists in the database, if yes, check the entered password
                if (snapshot.child(USERS).child(email).exists()) {
                    val user =
                        snapshot.child(USERS).child(email).child(
                            USER
                        )
                            .getValue(User::class.java)
                    // If the entered password matches the password in the database, create user preferences and finish the login process
                    if (user?.password!!.equals(binding.etTextPassword.text.toString())) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Successfully Login",
                            Toast.LENGTH_SHORT
                        ).show()
                        createPreferences(user)
                    } else {
                        // If the entered password does not match the password in the database, show an error message
                        Toast.makeText(
                            this@LoginActivity,
                            "The password is incorrect",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    // If the user does not exist in the database, show an error message
                    Toast.makeText(
                        this@LoginActivity,
                        "The email is incorrect",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Handle any database error
            }
        })
    }

    fun createPreferences(user: User) {
        val preferences = getSharedPreferences(USER, MODE_PRIVATE)
        preferences.edit().putString(USER_FIRST_NAME, user.firstName).commit()
        preferences.edit().putString(USER_LAST_NAME, user.lastName).commit()
        preferences.edit().putString(USER_EMAIL, user.email).commit()
        preferences.edit().putString(USER_PASSWORD, user.password).commit()
        preferences.edit().putBoolean(FIRST_TIME, false).commit()
        finishLogin()


    }

    fun finishLogin() {
        Intent(this, HomeActivity::class.java).apply {
            startActivity(this)
            finish()

        }
    }


}