package villalobos.diego.uni.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.recycler_comments.view.*
import villalobos.diego.uni.Data.Comment
import villalobos.diego.uni.R

class CommentAdapter (private val comments:ArrayList<Comment>, private val onClickListener: (Comment) -> Unit) :
        RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder{
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_comments,parent,false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position],onClickListener)
    }

    class CommentViewHolder(val layout:View ) : RecyclerView.ViewHolder(layout){
        fun bind (comment:Comment, clickListener: (Comment) -> Unit){
            layout.recycler_comments_text_name.text = comment.nombre
            layout.recycler_comments_text_codigo.text = comment.codigo
            layout.recycler_comments_text_date.text = comment.date
            layout.recycler_comments_text_comment.text = comment.comment
        }
    }

}