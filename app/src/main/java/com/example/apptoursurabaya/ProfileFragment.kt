package com.example.apptoursurabaya

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.apptoursurabaya.data.akun.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        auth = FirebaseAuth.getInstance()

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        btnLogout.setOnClickListener {
            auth.signOut()
            Intent(requireContext(), LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                Toast.makeText(requireContext(), "Logout Berhasil", Toast.LENGTH_SHORT).show()
            }
        }

        userInfo()

        return view
    }

    private fun userInfo(){
        val userRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<Users>(Users::class.java)
                val lihatNama = view!!.findViewById<TextView>(R.id.textView2)
                val lihatNamaLengkap = view!!.findViewById<EditText>(R.id.inputNamaLengkap)
                val lihatAlamat = view!!.findViewById<EditText>(R.id.inputAlamat)
                val lihatJk = view!!.findViewById<AutoCompleteTextView>(R.id.dropDown_field)
                val lihatEmail = view!!.findViewById<EditText>(R.id.inputUsername)
                val lihatPassword = view!!.findViewById<EditText>(R.id.inputPassword)


                lihatNama.text = Editable.Factory.getInstance().newEditable(user?.namaLengkap)
                lihatNamaLengkap.text = Editable.Factory.getInstance().newEditable(user?.namaLengkap)
                lihatAlamat.text = Editable.Factory.getInstance().newEditable(user?.alamat)
                lihatJk.text = Editable.Factory.getInstance().newEditable(user?.jenisKelamin)
                lihatEmail.text = Editable.Factory.getInstance().newEditable(firebaseUser.email)
                lihatPassword.text = Editable.Factory.getInstance().newEditable(user?.password)


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    companion object {
        // ...
    }
}