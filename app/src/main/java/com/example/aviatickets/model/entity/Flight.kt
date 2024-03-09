package com.example.aviatickets.model.entity

import com.google.gson.annotations.SerializedName

/**
 * Deserialization considering its snake_case format for Task 1
 */
data class Flight(
    @SerializedName("departure_location") val departureLocation: Location,
    @SerializedName("arrival_location") val arrivalLocation: Location,
    @SerializedName("departure_time_info") val departureTimeInfo: String,
    @SerializedName("arrival_time_info") val arrivalTimeInfo: String,
    @SerializedName("flight_number") val flightNumber: String,
    val airline: Airline,
    val duration: Int
)