package villalobos.diego.uni.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.recyler_anuncios.view.*
import villalobos.diego.uni.Data.Anuncio
import villalobos.diego.uni.R

class AnuncioAdapter (private val anuncios:ArrayList<Anuncio>,private val onClickListener: (Anuncio) -> Unit) :
        RecyclerView.Adapter<AnuncioAdapter.AnunciosViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnunciosViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyler_anuncios,parent,false)

        return AnunciosViewHolder(view)
    }

    override fun getItemCount(): Int {
        return anuncios.size
    }

    override fun onBindViewHolder(holder: AnunciosViewHolder, position: Int) {
        holder.bind(anuncios[position],onClickListener)
    }

    class AnunciosViewHolder(val layout : View) : RecyclerView.ViewHolder(layout) {
        fun bind(anuncio: Anuncio, clickListener: (Anuncio) -> Unit){
            Glide.with(layout.context).load(anuncio.photo).into(layout.recycler_anuncios_image_announcement)

            layout.recycler_anuncios_text_title.text = anuncio.titulo
            layout.recycler_anuncios_text_fecha.text = anuncio.fecha
            layout.recycler_anuncios_text_coordinacion.text = anuncio.coordinacion
            layout.setOnClickListener { clickListener(anuncio) }
        }
    }
}