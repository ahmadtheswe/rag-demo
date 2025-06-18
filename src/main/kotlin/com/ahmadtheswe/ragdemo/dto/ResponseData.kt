package com.ahmadtheswe.ragdemo.dto

data class ResponseData<T> (
    val data: T,
    val message: String? = null,
)