package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class XPrimeHubProvider : MainAPI() {
    override var mainUrl = "https://xprimehub.bond"
    override var name = "XPrimeHub"
    override val supportedTypes = setOf(TvType.NSFW)

    override var lang = "hi"
    override val hasMainPage = true

    override suspend fun getMainPage(page: Int, request: ExtraPage): HomePageResponse {
        val url = if (page <= 1) mainUrl else "$mainUrl/page/$page/"
        val doc = app.get(url).document
        
        val home = doc.select("article, div.post").mapNotNull { element ->
            val title = element.select("h2, .entry-title").text()
            val link = fixUrlNull(element.select("a").attr("href")) ?: return@mapNotNull null
            val poster = fixUrlNull(element.select("img").attr("src"))

            newMovieSearchResponse(title, link, TvType.NSFW) {
                this.posterUrl = poster
            }
        }
        return newHomePageResponse(name, home)
    }

    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.select("h1.entry-title").text()
        val poster = fixUrlNull(doc.select("div.entry-content img").attr("src"))

        return newMovieLoadResponse(title, url, TvType.NSFW, url) {
            this.posterUrl = poster
        }
    }

    // Fix: 'forEach' ki jagah standard 'for' loop use kiya gaya hai
    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        
        for (iframe in doc.select("iframe")) {
            val videoUrl = iframe.attr("src")
            if (videoUrl.isNotEmpty()) {
                loadExtractor(fixUrl(videoUrl), data, subtitleCallback, callback)
            }
        }
        return true
    }
}
