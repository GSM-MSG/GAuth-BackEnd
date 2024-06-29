package com.msg.gauth.global.util

object ExcelUtil {

    fun isExcel(mime: String, extension: String): Boolean =
        (mime == "application/x-tika-ooxml") && (extension == "xlsx" || extension == "xls")
}