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
import com.example.tasklist.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        initCliks()
    }

    private fun initCliks(){

        binding.btnLogin.setOnClickListener { validateData() }

        binding.btnRegister.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnRecover.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_recoveryAccountFragment)
        }
    }

    private fun validateData(){
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if(email.isNotEmpty()){
            if(password.isNotEmpty()){

                binding.progressBar.isVisible = true

                loginUser(email, password)

            }else{
                Toast.makeText(requireContext(), "Informe sua senha..", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(requireContext(), "Informe seu email.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String){

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_global_camActivity)
                } else {
                    binding.progressBar.isVisible = false
                }
            }

    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}

