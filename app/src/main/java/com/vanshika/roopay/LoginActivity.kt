package com.vanshika.roopay

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.vanshika.roopay.MainActivity
import com.vanshika.roopay.R
import com.vanshika.roopay.databinding.ActivityLoginBinding
import com.vanshika.roopay.SignUpActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    // Firebase Authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize View Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Handle Sign-in Button Click
        binding.btnSignin.setOnClickListener {
            val email = binding.loginEmailID.text.toString().trim()
            val password = binding.loginPassID.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                signInUser(email, password)
            } else {
                Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to SignUpActivity when "Sign-Up" is clicked
        binding.signUpID.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        // Handle Reset Password click
        binding.resetPassID.setOnClickListener {
            // Implement Password Reset functionality here
        }
    }

    // Method to sign in the user using Firebase Authentication
    private fun signInUser(email: String, password: String) {
        // Show Progress Bar
        binding.progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // Hide Progress Bar
                binding.progressBar.visibility = View.GONE

                if (task.isSuccessful) {
                    // Sign in success
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                    // Navigate to MainActivity after successful login
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Clear back stack
                    startActivity(intent)
                    finish() // Close the LoginActivity
                } else {
                    // Sign in failed
                    binding.linearID.visibility = View.VISIBLE
                    Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }
}
