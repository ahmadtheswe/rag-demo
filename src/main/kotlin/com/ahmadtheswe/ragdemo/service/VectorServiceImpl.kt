package com.ahmadtheswe.ragdemo.service

import com.ahmadtheswe.ragdemo.repository.VectorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class VectorServiceImpl(
  private val vectorRepository: VectorRepository
) : VectorService {

  private val logger = LoggerFactory.getLogger(VectorServiceImpl::class.java)

  override fun storeTextVectorData(content: String) {
    vectorRepository.storeTextVectorData(content).also {
      logger.info("Stored text vector data successfully")
    }
  }

  override fun storeFileVectorData(file: MultipartFile) {
    vectorRepository.storeFileVectorData(file).also {
      logger.info("Stored file vector data successfully from file: ${file.originalFilename}")
    }
  }

  override fun getSimilarDocuments(
    query: String,
    topK: Int?,
    threshold: Double?
  ): List<String?> {
    return vectorRepository.getSimilarDocuments(query, topK, threshold).also {
      logger.info("Retrieved ${it.size} similar documents for query: $query")
    }
  }
}