package com.dourl.drone

import java.lang.Exception

class ApiException(val code: Int,message:String) :Exception(message) {
}