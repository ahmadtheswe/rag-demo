package com.ahmadtheswe.ragdemo.dto

data class ResponseData<T>(
    val data: T,
    val message: String? = null,
    val status: Boolean = true
) {
    constructor(message: String) : this(data = null as T, message = message)
}
