package com.example.apptoursurabaya

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.apptoursurabaya.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView


@SuppressLint("CheckResult")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_login)

        // Auth
        auth = FirebaseAuth.getInstance()

        //validasi username
        val inputEmail = findViewById<EditText>(R.id.inputUsername)

        val emailStream = RxTextView.textChanges(inputEmail)
            .skipInitialValue()
            .map { email ->
                email.isEmpty()
            }
        emailStream.subscribe {
            showTextMinimalAlert(it, "Email")
        }

        //validasi password
        val inputPassword = findViewById<EditText>(R.id.inputPassword)

        val passwordStream = RxTextView.textChanges(inputPassword)
            .skipInitialValue()
            .map { password ->
                password.isEmpty()
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }

        // btn enable true or false
        val invalidFieldsStream = io.reactivex.Observable.combineLatest(
            emailStream,
            passwordStream,
            { usernameInvalid: Boolean, passwordInvalid: Boolean ->
                !usernameInvalid && !passwordInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                val btnLogin = findViewById<Button>(R.id.btnLogin)
                btnLogin.isEnabled = true
                btnLogin.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary)
            } else {
                val btnLogin = findViewById<Button>(R.id.btnLogin)
                btnLogin.isEnabled = false
                btnLogin.backgroundTintList = ContextCompat.getColorStateList(this,android.R.color.darker_gray)
            }
        }

        // btn klik
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val signup = findViewById<TextView>(R.id.signup)

        btnLogin.setOnClickListener {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            loginUser(email, password)
        }

        signup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {login ->
                if (login.isSuccessful) {
                    Intent(this, HomeActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
        val inputEmail = findViewById<EditText>(R.id.inputUsername)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)

        if (text == "Email")
            inputEmail.error = if (isNotValid) "$text tidak Boleh Kosong" else null
        else if (text == "Password")
            inputPassword.error = if (isNotValid) "$text tidak Boleh Kosong" else null
    }
}