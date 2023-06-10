package com.example.apptoursurabaya

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.database.Observable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.apptoursurabaya.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observer
import java.util.regex.Pattern


@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private lateinit var auth : FirebaseAuth

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        // Auth
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().getReference("USERS")

//        val btnDaftar = findViewById<Button>(R.id.btnDaftar)

        val items = listOf("Laki-laki", "Perempuan")

        val autoComplete : AutoCompleteTextView = findViewById(R.id.dropDown_field)

        val adapter = ArrayAdapter(this, R.layout.item_gender, items)

        autoComplete.setAdapter(adapter)

        //validasi nama lengkap
        val inputNamaLengkap = findViewById<EditText>(R.id.inputNamaLengkap)

        val nameStream = RxTextView.textChanges(inputNamaLengkap)
            .skipInitialValue()
            .map {name ->
                name.isEmpty()
            }
        nameStream.subscribe {
            showNameExistAlert(it)
        }

        //validasi Alamat
        val inputAlamat = findViewById<EditText>(R.id.inputAlamat)

        val alamatStream = RxTextView.textChanges(inputAlamat)
            .skipInitialValue()
            .map {alamat ->
                alamat.isEmpty()
            }
        alamatStream.subscribe {
            showAlamatExistAlert(it)
        }

        //validasi Email
        val inputEmail = findViewById<EditText>(R.id.inputUsername)

        val emailStream = RxTextView.textChanges(inputEmail)
            .skipInitialValue()
            .map{ email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidAlert(it)
        }

        //validasi password
        val inputPassword = findViewById<EditText>(R.id.inputPassword)

        val passwordStream = RxTextView.textChanges(inputPassword)
            .skipInitialValue()
            .map { password ->
                password.length < 8
            }
        passwordStream.subscribe {
            showTextMinimalAlert(it, "Password")
        }

        // btn enable true or false
        val invalidFieldsStream = io.reactivex.Observable.combineLatest(
            nameStream,
            alamatStream,
            emailStream,
            passwordStream,
            { nameInvalid: Boolean, alamatInvalid: Boolean,  emailInvalid: Boolean, passwordInvalid: Boolean ->
                !nameInvalid && !alamatInvalid && !emailInvalid && !passwordInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if (isValid) {
                val btnDaftar = findViewById<Button>(R.id.btnDaftar)
                btnDaftar.isEnabled = true
                btnDaftar.backgroundTintList = ContextCompat.getColorStateList(this, R.color.primary)
            } else {
                val btnDaftar = findViewById<Button>(R.id.btnDaftar)
                btnDaftar.isEnabled = false
                btnDaftar.backgroundTintList = ContextCompat.getColorStateList(this,android.R.color.darker_gray)
            }
        }


        // klik btn
        val btnDaftar = findViewById<Button>(R.id.btnDaftar)
        val signin = findViewById<TextView>(R.id.signin)
        val inputJk = findViewById<AutoCompleteTextView>(R.id.dropDown_field)



        btnDaftar.setOnClickListener {
            val namaLengkap = inputNamaLengkap.text.toString().trim()
            val alamat = inputAlamat.text.toString().trim()
            val jenisKelamin = inputJk.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            registerUser(namaLengkap,alamat,jenisKelamin,email,password)
        }

        signin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

//        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
//                adapterView, view, i, l ->
//            val itemSeleted = adapterView.getItemAtPosition(i)
//        }
    }

    private fun registerUser(namaLengkap: String,alamat: String, jenisKelamin: String ,email: String, password: String) {
        val progressDialog = ProgressDialog(this@RegisterActivity)
        progressDialog.setTitle("Registrasi User")
        progressDialog.setMessage("Tunggu Sebentar")
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
//                    Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                    saveUser(namaLengkap,alamat, jenisKelamin,email, password, progressDialog)
//                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    Toast.makeText(this, "Registrasi Gagal",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun saveUser(namaLengkap: String, alamat: String, jenisKelamin: String,email: String, password: String, progressDialog: ProgressDialog){
        val currentUserId = auth.currentUser!!.uid
        ref = FirebaseDatabase.getInstance().reference.child("USERS")
        val userMap = HashMap<String,Any>()

        userMap["id"] = currentUserId
        userMap["namaLengkap"] = namaLengkap
        userMap["alamat"] = alamat
        userMap["jenisKelamin"] = jenisKelamin
        userMap["email"] = email
        userMap["password"] = password
        ref.child(currentUserId).setValue(userMap).addOnCompleteListener {
            if (it.isSuccessful){
                progressDialog.dismiss()
                Toast.makeText(this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show()
                Intent(this, LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Registrasi Gagal", Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }

    }

    private fun showNameExistAlert(isNotValid: Boolean) {
        val inputNamaLengkap = findViewById<EditText>(R.id.inputNamaLengkap)

        inputNamaLengkap.error = if (isNotValid) "Nama Tidak Boleh Kosong" else null
    }

    private fun showTextMinimalAlert(isNotValid: Boolean, text: String) {
//        val inputUsername = findViewById<EditText>(R.id.inputUsername)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)
        if (text == "Username")
            inputPassword.error = if (isNotValid) "$text harus lebih dari 8 huruf" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean) {
        val inputEmail = findViewById<EditText>(R.id.inputUsername)
        inputEmail.error = if (isNotValid) "Email tidak valid" else null
    }

    private fun showAlamatExistAlert(isNotValid: Boolean) {
        val inputAlamat = findViewById<EditText>(R.id.inputAlamat)

        inputAlamat.error = if (isNotValid) "alamat tidak boleh kosong" else null
    }
}