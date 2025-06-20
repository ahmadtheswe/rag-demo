package com.ahmadtheswe.ragdemo.service

import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.SearchRequest
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

@Service
class VectorServiceImpl(private val vectorStore: VectorStore) : VectorService {

  private val logger = LoggerFactory.getLogger(VectorServiceImpl::class.java)

  override fun storeTextVectorData(content: String) {
    logger.info("Storing text vector data: $content")
    val document = Document(content, mapOf("source" to "direct-input"))
    vectorStore.add(listOf(document))
    logger.info("Text vector data stored successfully")
  }

  override fun storeFileVectorData(content: String, fileName: String) {
    logger.info("Storing file vector data from file: $fileName")
    val document = Document(content, mapOf("source" to "direct-input"))
    vectorStore.add(listOf(document))
    logger.info("File vector data stored successfully from file: $fileName")
  }

  override fun getSimilarDocuments(
    query: String,
    topK: Int?,
    threshold: Double?
  ): List<String?> {
    val searchRequest = SearchRequest.builder()
      .query(query)
      .topK(topK ?: 5)
      .similarityThreshold(threshold ?: 0.8)
      .build()
    logger.info("Retrieving similar documents for query: $query")
    val results: MutableList<Document>? = vectorStore.similaritySearch(searchRequest)
    return results?.take(topK ?: 5)?.map { it.text } ?: emptyList()
  }
}