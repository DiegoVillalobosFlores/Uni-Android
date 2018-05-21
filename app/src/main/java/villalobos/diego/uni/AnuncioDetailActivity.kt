package villalobos.diego.uni

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_anuncio_detail.*
import kotlinx.android.synthetic.main.content_anuncio_detail.*
import org.json.JSONObject
import villalobos.diego.uni.Adapters.CommentAdapter
import villalobos.diego.uni.Data.Anuncio
import villalobos.diego.uni.Data.Comment

class AnuncioDetailActivity : AppCompatActivity() {

    private lateinit var anuncio: Anuncio
    private lateinit var comments:ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anuncio_detail)
        setSupportActionBar(toolbar)

        anuncio = intent.getSerializableExtra("anuncio") as Anuncio
        bindAnuncio()

        getComments()
    }

    fun bindAnuncio(){
        Glide.with(this).load(anuncio.photo).into(anuncio_detail_image_photo)
        anuncio_detail_text_coordinacion.text = anuncio.coordinacion
        anuncio_detail_text_date.text = anuncio.fecha
        anuncio_detail_text_anuncio.text = anuncio.anuncio
        val title = "${anuncio.category}: ${anuncio.titulo}"
        anuncio_detail_text_title.text = title

    }

    fun getComments(){
        toggleProgressComments(true)
        val url = getString(R.string.API) + getString(R.string.API_GET_COMMENT)
        url.httpGet(listOf("announcement" to anuncio.id)).responseObject { request: Request, response: Response, result: Result<ArrayList<Comment>, FuelError> ->
            Log.d("COMMENTS REQ",request.toString())
            Log.d("COMMENTS RES",response.toString())
            Log.d("COMMENTS",result.toString())

            when(response.statusCode){
                200 -> {
                    comments = result.get()
                    toggleProgressComments(false)
                    drawCommentsRecycler()
                }
            }
        }
    }

    fun toggleCommentLayout(v: View){
        if(anuncio_detail_layout_comment.visibility == View.GONE){
            anuncio_detail_layout_comment.visibility = View.VISIBLE
        }else{
            anuncio_detail_layout_comment.visibility = View.GONE
        }
    }

    fun drawCommentsRecycler(){
        val viewAdapter = CommentAdapter(comments,{})
        val recyclerView = findViewById<RecyclerView>(R.id.anuncio_detail_recycler_comments).apply {
            setHasFixedSize(true)
            adapter = viewAdapter
        }
        recyclerView.isNestedScrollingEnabled = false
    }

    fun toggleProgressComments(show: Boolean){
        if(show){
            anuncio_detail_layout_comments.visibility = View.GONE
            anuncio_detail_progress_comments.visibility = View.VISIBLE
        }else{
            anuncio_detail_progress_comments.visibility = View.GONE
            anuncio_detail_layout_comments.visibility = View.VISIBLE
        }
    }

    fun postComment(v:View){
        Toast.makeText(this,"Enviando Comentario",Toast.LENGTH_LONG).show()
        val comment = Comment()
        comment.codigo = anuncio_detail_text_edit_code.text.toString()
        comment.nombre = anuncio_detail_text_edit_name.text.toString()
        comment.comment = anuncio_detail_text_edit_comment.text.toString()

        val body = JSONObject()
        body.put("codigo",comment.codigo)
        body.put("comment",comment.comment)
        body.put("nombre",comment.nombre)

        val json = JSONObject()
        json.put("comment",body)

        val url = getString(R.string.API) + getString(R.string.API_POST_COMMENT) + "?announcement=${anuncio.id}"
        val req = url.httpPost()
        req.headers["Content-Type"] = "application/json"
        req.body(json.toString())
        req.responseString { request, response, result ->
            Log.d("COMMENT REQ",request.toString())
            Log.d("COMMENT",response.toString())
            Log.d("COMMENT RESU",result.toString())
            if(response.statusCode == 200) {
                getComments()
                toggleCommentLayout(v)
            }else{
                Toast.makeText(this,"No se pudo enviar el comentario intentelo de nuevo",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
