package com.example.tasklist.ui.apiBrasil

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/placa/consulta")
    fun consultaPlaca(@Body request: PlacaRequest): Call<PlacaResponse>
}
