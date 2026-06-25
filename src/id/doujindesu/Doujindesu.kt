package eu.kanade.tachiyomi.extension.id.doujindesu

import eu.kanade.tachiyomi.network.GET
import eu.kanade.tachiyomi.source.model.FilterList
import eu.kanade.tachiyomi.source.model.Page
import eu.kanade.tachiyomi.source.model.SChapter
import eu.kanade.tachiyomi.source.model.SManga
import eu.kanade.tachiyomi.source.online.ParsedHttpSource
import okhttp3.Request
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class Doujindesu : ParsedHttpSource() {
    override val name = "Doujindesu"
    override val baseUrl = "https://doujin.desu.xxx"
    override val lang = "id"
    override val supportsLatest = true

    override fun headersBuilder() = super.headersBuilder()
        .add("Referer", "$baseUrl/")

    override fun popularMangaSelector() = "div.grid > div.group a[href]"
    override fun popularMangaNextPageSelector() = "a[rel='next'], a.next.page-numbers"
    override fun popularMangaRequest(page: Int): Request = GET("$baseUrl/manga?page=$page", headers)

    override fun popularMangaFromElement(element: Element) = SManga.create().apply {
        title = element.selectFirst("img")?.attr("alt") ?: element.text().trim().ifEmpty { "Unknown Title" }
        thumbnail_url = element.selectFirst("img")?.let { img ->
            img.attr("abs:data-src").ifEmpty { img.attr("abs:src") }
        }
        setUrlWithoutDomain(element.attr("abs:href"))
    }

    override fun latestUpdatesSelector() = popularMangaSelector()
    override fun latestUpdatesNextPageSelector() = popularMangaNextPageSelector()
    override fun latestUpdatesRequest(page: Int): Request = GET("$baseUrl/latest?page=$page", headers)
    override fun latestUpdatesFromElement(element: Element) = popularMangaFromElement(element)

    override fun searchMangaSelector() = popularMangaSelector()
    override fun searchMangaNextPageSelector() = popularMangaNextPageSelector()
    override fun searchMangaRequest(page: Int, query: String, filters: FilterList): Request =
        GET("$baseUrl/?search=$query&page=$page", headers)
    override fun searchMangaFromElement(element: Element) = popularMangaFromElement(element)

    override fun mangaDetailsParse(document: Document) = SManga.create().apply {
        title = document.selectFirst("h1")?.text() ?: ""
        thumbnail_url = document.selectFirst("img.h-full.w-full.object-cover")?.let { img ->
            img.attr("abs:data-src").ifEmpty { img.attr("abs:src") }
        }
        description = document.selectFirst("div.prose, div.description")?.text()
    }

    override fun chapterListSelector() = "div.flex.items-center.gap-2:has(h3)"
    override fun chapterFromElement(element: Element) = SChapter.create().apply {
        name = element.selectFirst("h3")?.text() ?: "Chapter"
        val
        
        
