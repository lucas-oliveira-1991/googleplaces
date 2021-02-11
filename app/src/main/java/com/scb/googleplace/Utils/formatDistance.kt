package com.scb.googleplace.Utils

class formatDistance() : Dist {

    override fun getDist(dist: Int): String {
        var d = "";
      if (dist==0)
            d = "1000"
        if (dist==1)
            d = "5000"
        if (dist==2)
            d = "10000"
        if (dist==3)
            d = "25000"
        return d
    }




}

interface Dist {
    fun getDist (dist : Int) : String
}