package com.docter.docter.utils

interface OnPlacesDetailsListener {
    /**
     * Triggered when the places details are fetched and returns the details of the pace
     */
    fun onPlaceDetailsFetched(placeDetails: PlaceDetails)

    fun onGeoCodeDetailsFetched(zipcode: String)
    fun onGeoCodeAddressDetailsFetched(lat: Double, lng: Double)

    /**
     * Triggered when there is an error and passes the error message along as a parameter
     */
    fun onError(errorMessage: String)
}