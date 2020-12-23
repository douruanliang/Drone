package com.dourl.drone


/**
 * to custom error response model
 */
interface ApiErrorModelMapper<V> {
    fun map(apiErrorResponse: ApiResponse.Failure.Error<*>) :V
}