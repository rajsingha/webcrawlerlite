import Networks.FetchUrlsData


fun main() {
    val array = arrayOf("https://google.com","https://fb.com")
    getData(array)

}

fun getData(array: Array<String>) {
    for (url in array){
        val siteData = FetchUrlsData(url)
        siteData.getLinks()
    }
}

