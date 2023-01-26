package com.example.tasklist.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.tasklist.R
import com.example.tasklist.databinding.FragmentLoginBinding
import com.example.tasklist.databinding.FragmentRecoveryAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RecoveryAccountFragment : Fragment() {

    private var _binding: FragmentRecoveryAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoveryAccountBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initCliks()
    }
    private fun initCliks() {
        binding.btnSend.setOnClickListener { validateData() }
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()

        if(email.isNotEmpty()){
            binding.progressBar.isVisible = true

            recoverAccountUser(email)

        }else{
            Toast.makeText(requireContext(), "Informe seu email.", Toast.LENGTH_SHORT).show()
        }
    }
    private fun recoverAccountUser(email: String){

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireContext(),"O Link de recuperação de senha foi enviado para seu E-mail", Toast.LENGTH_SHORT).show()
                }
                binding.progressBar.isVisible = false
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

}