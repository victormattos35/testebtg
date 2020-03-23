package br.com.testebtg.util

import android.content.Context
import android.widget.ImageView
import br.com.testebtg.R
import com.squareup.picasso.Picasso

fun getImageUrl(context:Context, path: String?, imageView: ImageView){
    Picasso.with(context)
        .load("https://image.tmdb.org/t/p/w500$path")
        .placeholder(R.drawable.ic_launcher_background)
        .into(imageView)
}