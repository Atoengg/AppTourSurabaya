package com.example.apptoursurabaya

import android.os.Bundle
import android.text.Editable
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.apptoursurabaya.data.CardList
import com.example.apptoursurabaya.data.akun.Users
import com.example.apptoursurabaya.databinding.FragmentHomeBinding
import com.example.apptoursurabaya.ui.CardAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var adapter: CardAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        adapter = CardAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = adapter
        adapter.submitList(CardList.getCardList())
        binding.recyclerView.setHasFixedSize(true)

        binding.btnSearch.setOnClickListener {
            val query = binding.etSearch.text.toString()
            performSearch(query)
        }

        userInfo()
    }

    private fun performSearch(query: String) {
        val filteredList = CardList.getCardList().filter { card ->
            card.name.contains(query, true)
        }
        adapter.submitList(filteredList)
    }

    private fun userInfo() {
        val userRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(firebaseUser.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue<Users>(Users::class.java)
                val lihatNama = binding.textView2

                lihatNama.text = user?.namaLengkap
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled event
            }
        })
    }
}


