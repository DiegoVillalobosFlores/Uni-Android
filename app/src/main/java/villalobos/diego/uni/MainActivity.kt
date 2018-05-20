package villalobos.diego.uni

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import kotlinx.android.synthetic.main.activity_main.*
import villalobos.diego.uni.Adapters.AnuncioAdapter
import villalobos.diego.uni.Data.Anuncio

class MainActivity : AppCompatActivity() {

    private lateinit var viewAdapter: AnuncioAdapter
    private lateinit var anuncios:ArrayList<Anuncio>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getAnuncios()
    }

    fun getAnuncios(){
        switchScrollProgressVisibility(true)
        val url = getString(R.string.API) + getString(R.string.API_GET_ANNOUNCEMENTS)
        url.httpGet().responseObject { request: Request, response: Response, result: Result<ArrayList<Anuncio>, FuelError> ->
            Log.d("RESU",result.toString())

            when(response.statusCode){
                200 -> {
                    anuncios = result.get()
                    switchScrollProgressVisibility(false)
                    drawAnunciosRecycler()
                }
            }
        }
    }

    fun switchScrollProgressVisibility(show:Boolean){
        if(show){
            main_nested.visibility = View.GONE
            main_progress_anuncios.visibility = View.VISIBLE
        }else{
            main_progress_anuncios.visibility = View.GONE
            main_nested.visibility = View.VISIBLE
        }
    }

    fun drawAnunciosRecycler(){
        viewAdapter = AnuncioAdapter(anuncios,{anuncio: Anuncio -> {} })

        val recycler = findViewById<RecyclerView>(R.id.main_recycler_announcements).apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
        recycler.isNestedScrollingEnabled = false
    }
}
