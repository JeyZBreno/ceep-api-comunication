package br.com.alura.ceep.data.service

import br.com.alura.ceep.data.model.NotaResponse
import br.com.alura.ceep.model.Nota
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface NotaApi {

    @GET("notas")
    suspend fun buscaTodas(): List<NotaResponse>
}