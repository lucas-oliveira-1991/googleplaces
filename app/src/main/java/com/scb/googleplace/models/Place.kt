package com.scb.placemaps.models

import com.google.gson.annotations.SerializedName

class Place {

    @SerializedName( "results" )
    var results: List<Candidates>? =  null

}

class  Candidates {

    @SerializedName( "name" )
    var name: String? =  null

    @SerializedName( "formatted_address" )
    var formatted_address: String? =  null

    @SerializedName( "rating" )
    var rating: String? =  null

    @SerializedName( "geometry" )
    var geometry: Geometry? =  null

    @SerializedName( "icon" )
    var icon: String? =  null
}

class  Geometry {
    @SerializedName ( "location" )
    var location: Location? = null
    @SerializedName ( "viewport" )
    var viewport: Viewport? = null
}


class  Location {
    @SerializedName ( "lng" )
    var lng: Float = 0.toFloat ()
    @SerializedName ( "lat" )
    var lat: Float = 0.toFloat ()
}


class  Viewport {
    @SerializedName ( "northeast" )
    var northeast: Northeast? = null
    @SerializedName ( "southwest" )
    var southwest: Southwest? = null
}

class  Northeast {
    @SerializedName ( "lng" )
    var lng: Float = 0.toFloat ()
    @SerializedName ( "lat" )
    var lat: Float = 0.toFloat ()
}
class  Southwest {
    @SerializedName ( "lng" )
    var lng: Float = 0.toFloat ()
    @SerializedName ( "lat" )
    var lat: Float = 0.toFloat ()
}