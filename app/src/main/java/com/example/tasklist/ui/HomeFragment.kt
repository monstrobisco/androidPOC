package com.example.tasklist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tasklist.R
import com.example.tasklist.databinding.FragmentHomeBinding
import com.example.tasklist.ui.auth.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cam.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        configTablayout()

        initClicks()
    }

    private fun initClicks(){

        binding.ibLogout.setOnClickListener { logoutApp() }

    }

    private fun logoutApp(){
        auth.signOut()
        findNavController().navigate(R.id.action_homeFragment_to_authentication)
    }

/*    private fun openCam(){

        findNavController().navigate(R.id.action_splashFragment_to_camActivity)
    }*/
    private fun configTablayout() {
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = adapter

        adapter.addFragment(TodoFragment(),"Consultar Placa")
        adapter.addFragment(DoingFragment(),"Gerar QR")
        adapter.addFragment(DoneFragment(),"Ler QR")

        binding.viewPager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabs, binding.viewPager
        ) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}