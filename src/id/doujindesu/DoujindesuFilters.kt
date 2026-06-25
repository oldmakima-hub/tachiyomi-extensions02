package eu.kanade.tachiyomi.extension.id.doujindesu

import eu.kanade.tachiyomi.source.model.Filter
import eu.kanade.tachiyomi.source.model.FilterList

fun getFilters(): FilterList {
    val filters = mutableListOf<Filter<*>>()

    filters.add(Filter.Header("Catatan: filter mungkin tidak bekerja"))
    filters.add(Filter.Separator())

    filters.add(GenreFilter())
    filters.add(StatusFilter())
    filters.add(SortFilter())

    return FilterList(filters)
}

private class GenreFilter : Filter.Select<String>(
    "Genre",
    arrayOf(
        "Semua",
        "Action",
        "Adventure",
        "Comedy",
        "Drama",
        "Ecchi",
        "Fantasy",
        "Horror",
        "Romance",
        "School Life",
        "Sci-Fi",
        "Slice of Life",
        "Sports",
        "Supernatural"
    )
)

private class StatusFilter : Filter.Select<String>(
    "Status",
    arrayOf(
        "Semua",
        "Ongoing",
        "Completed"
    )
)

private class SortFilter : Filter.Select<String>(
    "Urutkan",
    arrayOf(
        "Terbaru",
        "Populer",
        "A-Z",
        "Z-A"
    )
)

