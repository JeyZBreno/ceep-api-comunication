package br.com.alura.ceep.data.service

import br.com.alura.ceep.data.model.NotaRequest
import br.com.alura.ceep.data.model.NotaResponse
import br.com.alura.ceep.model.Nota
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotaApi {

    @GET("notas")
    suspend fun buscaTodas(): List<NotaResponse>

    @PUT("notas/{id}")
    suspend fun salva(
        @Path("id") id: String,
        @Body nota: NotaRequest,
    ): Response<NotaResponse>

    @DELETE("notas/{id}")
    suspend fun remove(
        @Path("id") id: String,
    ): Response<Unit>
}