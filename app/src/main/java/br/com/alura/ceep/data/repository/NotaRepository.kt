package br.com.alura.ceep.data.repository

import br.com.alura.ceep.data.NotaApiImpl
import br.com.alura.ceep.database.dao.NotaDao
import br.com.alura.ceep.model.Nota
import kotlinx.coroutines.flow.Flow

class NotaRepository(
    private val dao: NotaDao,
    private val notaApi: NotaApiImpl,
) {

    fun buscaTodas(): Flow<List<Nota>> {
        return dao.buscaTodas()
    }

    suspend fun atualizaTodas() {
        notaApi.buscaTodas()?.let { notas ->
            dao.salva(notas)
        }
    }


}