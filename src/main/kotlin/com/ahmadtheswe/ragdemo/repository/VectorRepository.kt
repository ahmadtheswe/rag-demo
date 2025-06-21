package com.ahmadtheswe.ragdemo.repository

import org.springframework.web.multipart.MultipartFile

interface VectorRepository {
  fun storeTextVectorData(content: String)
  fun storeFileVectorData(file: MultipartFile)
  fun getSimilarDocuments(query: String, topK: Int?, threshold: Double?): List<String?>
}