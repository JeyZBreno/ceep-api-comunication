package br.com.alura.ceep.data.repository

import br.com.alura.ceep.data.NotaApiImpl
import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class NotaRepository(
    private val dao: NotaDao,
    private val notaApi: NotaApiImpl,
) {

    fun buscaTodas(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    private suspend fun atualizaTodas() {
        notaApi.buscaTodas()?.let { notas ->
            val notasSincronizadas = notas.map { nota ->
                nota.copy(sincronizada = true)
            }
            dao.salva(notasSincronizadas)
        }
    }

    fun buscaPorId(id: String): Flow<Nota> {
        return dao.buscaPorId(id)
    }

    suspend fun remove(id: String) {
        dao.desativa(id)
        if(notaApi.remove(id)) {
            dao.remove(id)
        }
    }

    suspend fun salva(nota: Nota) {
        dao.salva(nota)
        if(notaApi.salva(nota)) {
            val notaSincronizada = nota.copy(sincronizada = true)
            dao.salva(notaSincronizada)
        }
    }

    suspend fun sincroniza() {
        val notasDesativadas = dao.BuscaDesativadas().first()
        val notasNaoSincronizadas = dao.buscaNaoSincronizada().first()
        notasDesativadas.forEach { notaDesativada ->
            remove(notaDesativada.id)
        }
        notasNaoSincronizadas.forEach { notaNaoSincronizada ->
            salva(notaNaoSincronizada)
        }
        atualizaTodas()
    }
}