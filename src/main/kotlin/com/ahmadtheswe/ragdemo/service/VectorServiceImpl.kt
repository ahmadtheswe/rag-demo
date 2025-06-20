package com.ahmadtheswe.ragdemo.service

import com.ahmadtheswe.ragdemo.service.VectorService
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
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
}