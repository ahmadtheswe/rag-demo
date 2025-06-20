package com.ahmadtheswe.ragdemo.service

import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class VectorServiceImpl(private val vectorStore: VectorStore) : VectorService {

  private val logger = LoggerFactory.getLogger(VectorServiceImpl::class.java)

  override fun storeTextVectorData(content: String) {
    try {
      logger.info("Storing text vector data: $content")
      val document = Document(content, mapOf("source" to "direct-input"))
      vectorStore.add(listOf(document))
      logger.info("Text vector data stored successfully")
    } catch (e: Exception) {
      logger.error("Error storing text vector data", e)
      throw RuntimeException("Failed to store text vector data: ${e.message}", e)
    }
  }

  override fun storeFileVectorData(file: MultipartFile) {
    try {
      if (!file.originalFilename?.lowercase()?.endsWith(".txt")!!) {
        throw IllegalArgumentException("Only .txt files are allowed")
      }

      val fileName = file.originalFilename ?: "unknown"

      val content = String(file.bytes)
      logger.info("Storing file vector data from file: $fileName")
      val document = Document(content, mapOf("source" to "direct-input"))
      vectorStore.add(listOf(document))
      logger.info("File vector data stored successfully from file: $fileName")
    } catch (e: Exception) {
      logger.error("Error storing file vector data", e)
      throw RuntimeException("Failed to store file vector data: ${e.message}", e)
    }
  }

  override fun getSimilarDocuments(
    query: String,
    topK: Int?,
    threshold: Double?
  ): List<String?> {
    try {
      val searchRequest = SearchRequest.builder()
        .query(query)
        .topK(topK ?: 5)
        .similarityThreshold(threshold ?: 0.8)
        .build()
      logger.info("Retrieving similar documents for query: $query")
      val results: MutableList<Document>? = vectorStore.similaritySearch(searchRequest)
      return results?.take(topK ?: 5)?.map { it.text } ?: emptyList()
    } catch (e: Exception) {
      logger.error("Error retrieving similar documents", e)
      throw RuntimeException("Failed to retrieve similar documents: ${e.message}", e)
    }
  }
}