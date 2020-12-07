package com.dourl.drone.network

import com.dourl.drone.ApiErrorModelMapper
import com.dourl.drone.ApiResponse
import com.dourl.drone.message
import com.dourl.drone.model.ErrorEnvelope

object ErrorEnvelopeMapper :ApiErrorModelMapper<ErrorEnvelope> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorEnvelope {
        return ErrorEnvelope(apiErrorResponse.statusCode.code,apiErrorResponse.message())
    }
}