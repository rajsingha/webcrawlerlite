package helper

import Networks.CheckSiteStatus
import database.DataBaseHelper

object Tools {
    private var dbConnection: DataBaseHelper? = null

    fun print(msg: String, vararg args: Any?) {
        println(String.format(msg, *args))
    }

    fun trimIt(str: String, width: Int): String {
        return if (str.length > width) str.substring(0, width - 1) + "." else str
    }

    fun String.createDb() {
        dbConnection = DataBaseHelper()
        dbConnection?.initConnection(this)
        print("Db created successfully!")
    }

    fun DataBaseHelper.insertDataInMainTable(
        uuid: Int,
        mainUrl: String,
        mainSiteName: String,
        siteSize: String, responseTime: String, status: String, totalLinks: Int
    ) {
        this.updateQuery("INSERT INTO ${Constant.TBL_MAIN_URL} ${Constant.TBL_MAIN_URL_VALUES} values( $uuid,'$mainUrl','$mainSiteName','$siteSize','$responseTime','$status',$totalLinks)")
    }

    fun DataBaseHelper.insertDataInSubTable(
        uuid: Int,
        suid: Int,
        subUrl: String,
        subSiteName: String,
        responseTime: String,
        status: String,
        siteSize: String
    ) {
        this.updateQuery("INSERT INTO ${Constant.TBL_SUB_URL} ${Constant.TBL_SUB_URL_VALUES} values( $uuid,'$suid','$subUrl','$subSiteName','$responseTime','$status','$siteSize')")
    }

    fun getRandomNumber(min: Int, max: Int): Int {
        return (Math.random() * (max - min) + min).toInt()
    }

    fun CheckSiteStatus.getSizeInKb(url: String): String? {
        return this.getFileSize(url).toString() + "kb"
    }

    fun CheckSiteStatus.getResponseTime(url: String): String? {
        return this.urlResponseTime(url).toString() + "ms"
    }
}