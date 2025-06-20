package com.ahmadtheswe.ragdemo.service

import org.springframework.web.multipart.MultipartFile

interface VectorService {
  fun storeTextVectorData(content: String)
  fun storeFileVectorData(file: MultipartFile)
  fun getSimilarDocuments(query: String, topK: Int?, threshold: Double?): List<String?>
}