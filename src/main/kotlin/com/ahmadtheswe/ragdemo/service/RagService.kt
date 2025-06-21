package com.ahmadtheswe.ragdemo.service

interface RagService {
  fun ask(question: String, threshold: Double?): String?
}