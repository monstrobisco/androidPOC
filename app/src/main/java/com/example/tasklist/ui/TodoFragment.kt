package com.example.tasklist.ui


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasklist.R
import com.example.tasklist.databinding.FragmentTodoBinding
import com.example.tasklist.ui.apiBrasil.PlacaRequest
import com.example.tasklist.ui.apiBrasil.PlacaResponse
import com.example.tasklist.ui.apiBrasil.RetrofitClient
import kotlinx.android.synthetic.main.activity_cam.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TodoFragment : Fragment() {

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//###############################Consulta API Brasil################################################
        val apiService = RetrofitClient.getService()
        val request = PlacaRequest("OAE2911")
        val call = apiService.consultaPlaca(request)

        call.enqueue(object : Callback<PlacaResponse> {
            override fun onResponse(call: Call<PlacaResponse>, response: Response<PlacaResponse>) {
                if (response.isSuccessful) {
                    val placaResponse = response.body()
                    // faça algo com a resposta
                    println(placaResponse)
                }
            }
            override fun onFailure(call: Call<PlacaResponse>, t: Throwable) {
                // trate a falha
            }
        })
//###############################Consulta API Brasil################################################

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
