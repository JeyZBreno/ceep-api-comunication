package br.com.alura.ceep.data.model

data class NotaRequest(
    val titulo: String,
    val descricao: String,
    val imagem: String? = null,
)