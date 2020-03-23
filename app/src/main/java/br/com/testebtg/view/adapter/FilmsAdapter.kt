package br.com.testebtg.view.adapter

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.testebtg.R
import br.com.testebtg.model.Film
import br.com.testebtg.util.formatDate
import br.com.testebtg.util.getImageUrl
import br.com.testebtg.view.DescriptionActivity
import br.com.testebtg.viewmodel.FavoritesViewModel
import kotlinx.android.synthetic.main.list_films.view.*

class FilmsAdapter(
    private var films: List<Film>,
    private val context: Context
) :
    RecyclerView.Adapter<FilmsAdapter.ViewHolder>() {

    private val viewModelFavorites: FavoritesViewModel = FavoritesViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_films, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return films.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val film = films[position]
        if (!film.poster_path.isBlank()) {
            getImageUrl(context, film.poster_path, holder.ivFilm)
        }
        holder.tvNameFilm.text = film.title
        holder.tvYearFilm.text = formatDate(film.release_date)

        holder.clCallDescription.setOnClickListener {
            Intent(context, DescriptionActivity::class.java).also {
                it.putExtra("film", film)
                context.startActivity(it)
            }
        }

        if (film.isFavorite) {
            holder.ibFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_star_gold_24dp))
        } else holder.ibFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_star_border_gold_24dp))

        holder.ibFavorite.setOnClickListener {
            film.isFavorite = !film.isFavorite
            if (film.isFavorite) {
                viewModelFavorites.insertFilmInFavorite(context, film)
                holder.ibFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_star_gold_24dp))
            } else {
                viewModelFavorites.deleteFilmInFavorite(context, film)
                holder.ibFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_star_border_gold_24dp))
            }
            viewModelFavorites.getAllFavorites(context)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivFilm: ImageView = itemView.ivLFPoster
        val tvNameFilm: TextView = itemView.tvLFNameFilm
        val tvYearFilm: TextView = itemView.tvLFYear
        val clCallDescription: ConstraintLayout = itemView.clLFFilmItem
        var ibFavorite: ImageButton = itemView.ibLFFavorite
    }

}