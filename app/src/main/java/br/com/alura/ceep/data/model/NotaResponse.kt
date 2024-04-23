package br.com.alura.ceep.data.model

import br.com.alura.ceep.model.Nota
import java.util.UUID

class NotaResponse(
    val id: String?,
    val titulo: String?,
    val descricao: String?,
    val imagem: String?,
) {

    val nota: Nota
        get() = Nota(
            id = id ?: UUID.randomUUID().toString(),
            titulo = titulo ?: "",
            descricao = descricao ?: "",
            imagem = imagem ?: "",
        )
}