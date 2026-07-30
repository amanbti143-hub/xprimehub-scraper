// ✅ FIXED CODE
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
