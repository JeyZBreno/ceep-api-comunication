package br.com.alura.ceep.data.model

import br.com.alura.ceep.model.Nota

class NotaResponse(
    val id: String?,
    val titulo: String?,
    val descricao: String?,
    val imagem: String?,
) {

    val nota: Nota
        get() = Nota(
            id = 0,
            titulo = titulo ?: "",
            descricao = descricao ?: "",
            imagem = imagem ?: "",
        )
}