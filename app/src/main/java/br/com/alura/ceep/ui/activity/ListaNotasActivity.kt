package br.com.alura.ceep.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.alura.ceep.data.NotaApiImpl
import br.com.alura.ceep.data.builder.RetrofitBuilder
import br.com.alura.ceep.data.model.NotaResponse
import br.com.alura.ceep.data.repository.NotaRepository
import br.com.alura.ceep.database.AppDatabase
import br.com.alura.ceep.databinding.ActivityListaNotasBinding
import br.com.alura.ceep.extensions.vaiPara
import br.com.alura.ceep.model.Nota
import br.com.alura.ceep.ui.recyclerview.adapter.ListaNotasAdapter
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET

class ListaNotasActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityListaNotasBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        ListaNotasAdapter(this)
    }

    private val repository by lazy {
        NotaRepository(
            AppDatabase.instancia(this).notaDao(),
            NotaApiImpl(),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraFab()
        configuraRecyclerView()
        lifecycleScope.launch {
            launch {
                atualizaTodas()
            }
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                buscaNotas()
            }
        }
    }

    private suspend fun atualizaTodas() {
        repository.atualizaTodas()
    }

    private fun retrofitSemCoroutines() {

        //          Api Service
        //        @GET("notas")
        //        fun buscaTodas(): Call<List<NotaResponse>>
        //
        //
        //          Implementação do Service
        //        val call: Call<List<NotaResponse>> = RetrofitBuilder().notaService.buscaTodas()
        //        lifecycleScope.launch(IO) {
        //
        //            val response: Response<List<NotaResponse>> = call.execute()
        //            response.body()?.let { notasResponse ->
        //                val notas: List<Nota> = notasResponse.map {
        //                    it.nota
        //                }
        //                Log.i("ListaNotas", "onCreate: $notas")
        //            }
        //        }
        //        call.enqueue(object : Callback<List<NotaResponse>?> {
        //            override fun onResponse(
        //                call: Call<List<NotaResponse>?>,
        //                response: Response<List<NotaResponse>?>
        //            ) {
        //                response.body()?.let { notasResponse ->
        //                    val notas: List<Nota> = notasResponse.map {
        //                        it.nota
        //                    }
        //                    Log.i("ListaNotas", "onCreate: $notas")
        //                }
        //            }
        //
        //            override fun onFailure(call: Call<List<NotaResponse>?>, t: Throwable) {
        //                Log.e("ListaNotas", "onFaliure: ", t)
        //            }
        //        })
    }

    private fun configuraFab() {
        binding.activityListaNotasFab.setOnClickListener {
            Intent(this, FormNotaActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun configuraRecyclerView() {
        binding.activityListaNotasRecyclerview.adapter = adapter
        adapter.quandoClicaNoItem = { nota ->
            vaiPara(FormNotaActivity::class.java) {
                putExtra(NOTA_ID, nota.id)
            }
        }
    }

    private suspend fun buscaNotas() {
        repository.buscaTodas()
            .collect { notasEncontradas ->
                binding.activityListaNotasMensagemSemNotas.visibility =
                    if (notasEncontradas.isEmpty()) {
                        binding.activityListaNotasRecyclerview.visibility = GONE
                        VISIBLE
                    } else {
                        binding.activityListaNotasRecyclerview.visibility = VISIBLE
                        adapter.atualiza(notasEncontradas)
                        GONE
                    }
            }
    }
}