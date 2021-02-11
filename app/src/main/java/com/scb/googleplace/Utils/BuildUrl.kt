package com.scb.googleplace.Utils

import retrofit2.Retrofit

class BuildUrl (local: String, dist : String, type : String){

    var initPath = "json?location="
    val body = "&keyword=cruise&key=AIzaSyCdp_jkSbIbzGBKz1A3TZ37t8W65YhavNk"

    val url = "$initPath$local&radius=$dist&type=$type$body"

}