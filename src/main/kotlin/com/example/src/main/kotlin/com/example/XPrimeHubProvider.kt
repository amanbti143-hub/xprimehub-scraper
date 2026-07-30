// ❌ CRITICAL ERROR (Source 5)
doc.select("iframe").forEach { iframe ->
    val videoUrl = iframe.attr("src")
    if (videoUrl.isNotEmpty()) {
        // Error: 'loadExtractor' suspend function hai, ise standard 'forEach' lambda ke andar call nahi kar sakte.
        loadExtractor(videoUrl, data, subtitleCallback, callback) 
    }
}
