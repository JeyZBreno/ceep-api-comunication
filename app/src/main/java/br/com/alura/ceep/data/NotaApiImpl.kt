package br.com.alura.ceep.data

import android.util.Log
import br.com.alura.ceep.data.builder.RetrofitBuilder
import br.com.alura.ceep.data.model.NotaRequest
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
            Log.e(TAG, "buscaTodas", e)
            null
        }
    }

    suspend fun salva(nota: Nota): Boolean {
        try {
            val response = notaApi.salva(
                id = nota.id,
                nota = NotaRequest(
                    titulo = nota.titulo,
                    descricao = nota.descricao,
                    imagem = nota.imagem,
                )
            )
            return response.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "salva: Falha ao tentar salvar", e)
        }
        return false
    }

    suspend fun remove(id: String): Boolean {
        try {
            val response = notaApi.remove(id = id)
            return response.isSuccessful
        } catch (e: Exception) {
            Log.e(TAG, "remove: Falha ao tentar remover", e)
        }
        return false
    }
}