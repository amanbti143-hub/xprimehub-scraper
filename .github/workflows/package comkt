package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.nodes.Document

class XPrimeHubProvider : MainAPI() { // Provider class ka naam
    override var mainUrl = "https://xprimehub.bond"
    override var name = "XPrimeHub"
    override val supportedTypes = setOf(TvType.NSFW) // Category set karein

    override var lang = "hi"
    override val hasMainPage = true

    // 1. Home Page / List Parse karne ke liye
    override suspend fun getMainPage(page: Int, request: ExtraPage): HomePageResponse {
        val url = if (page <= 1) mainUrl else "$mainUrl/page/$page/"
        val doc = app.get(url).document
        
        // Screenshot me jaise <a> tags / posts the, waise HTML parse hoga
        val home = doc.select("article, div.post").mapNotNull { element ->
            val title = element.select("h2, .entry-title").text()
            val link = element.select("a").attr("href")
            val poster = element.select("img").attr("src")

            if (link.isEmpty()) return@mapNotNull null

            newMovieSearchResponse(title, link, TvType.NSFW) {
                this.posterUrl = poster
            }
        }
        return newHomePageResponse(name, home)
    }

    // 2. Details / Video Page Parse karne ke liye
    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.select("h1.entry-title").text()
        val poster = doc.select("div.entry-content img").attr("src")

        return newMovieLoadResponse(title, url, TvType.NSFW, url) {
            this.posterUrl = poster
        }
    }

    // 3. Direct Streaming Link Extract karne ke liye
    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        
        // Video player ya iframe ka source link nikalne ki logic
        doc.select("iframe").forEach { iframe ->
            val videoUrl = iframe.attr("src")
            if (videoUrl.isNotEmpty()) {
                // Cloudstream auto-extractors ko call karega
                loadExtractor(videoUrl, data, subtitleCallback, callback)
            }
        }
        return true
    }
}
