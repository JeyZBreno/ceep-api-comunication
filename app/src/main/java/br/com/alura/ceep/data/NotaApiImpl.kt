package br.com.alura.ceep.data

import android.util.Log
import br.com.alura.ceep.data.builder.RetrofitBuilder
import br.com.alura.ceep.data.service.NotaApi
import br.com.alura.ceep.model.Nota
import java.lang.Exception

private const val TAG = "NotaApiImpl"

class NotaApiImpl {

    private val notaApi: NotaApi = RetrofitBuilder().notaApi

    suspend fun buscaTodas(): List<Nota>? {
        return try {
            val notasResponse = notaApi.buscaTodas()
            notasResponse.map { notaResponse ->
                notaResponse.nota
            }
        } catch (e: Exception) {
            Log.e(TAG,"buscaTodas", e)
            null
        }
    }
}