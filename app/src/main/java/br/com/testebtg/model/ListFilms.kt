package br.com.testebtg.model

import java.io.Serializable


data class ListFilms(
    var page: Int,
    var results: List<Film>,
    var total_results: Double,
    var total_pages: Int
) : Serializable {
    constructor() : this(1, ArrayList<Film>(), 12.0, 2)
}