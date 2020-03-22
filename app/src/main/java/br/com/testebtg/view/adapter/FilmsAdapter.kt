package br.com.testebtg.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.testebtg.R
import br.com.testebtg.model.Film
import br.com.testebtg.util.formatDate
import br.com.testebtg.util.getImageUrl
import br.com.testebtg.view.DescriptionActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_films.view.*

class FilmsAdapter(
    private val films: List<Film>,
    private val context: Context
) :
    RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_films, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        if (!film.poster_path.isBlank()) {
            getImageUrl(context, film.poster_path, holder.imageFilm)
        }
        holder.nameFilm.text = film.title
        holder.yearFilm.text = formatDate(film.release_date)

        holder.clCallDescription.setOnClickListener {
            Intent(context, DescriptionActivity::class.java).also {
                it.putExtra("film", film)
                context.startActivity(it)
            }
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageFilm: ImageView = itemView.ivPoster
        val nameFilm: TextView = itemView.tvNameFilm
        val yearFilm: TextView = itemView.tvYear
        var clCallDescription: ConstraintLayout = itemView.clFilmItem
    }

}