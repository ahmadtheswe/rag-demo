package com.ahmadtheswe.ragdemo.service

interface VectorService {
  fun storeTextVectorData(content: String)
  fun storeFileVectorData(content: String, fileName: String)
  fun getSimilarDocuments(query: String, topK: Int? = 5): List<String?>
}