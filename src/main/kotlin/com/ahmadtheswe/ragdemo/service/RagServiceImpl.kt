package com.ahmadtheswe.ragdemo.service

import com.ahmadtheswe.ragdemo.repository.VectorRepository
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class RagServiceImpl(
  chatClientBuilder: ChatClient.Builder, private val vectorRepository: VectorRepository
) : RagService {
  private val logger = LoggerFactory.getLogger(RagServiceImpl::class.java)
  private val chatClient: ChatClient = chatClientBuilder.build()

  override fun ask(question: String, threshold: Double?): String? {
    try {
      logger.info("Getting similar documents for question: $question")
      val relevantDocuments =
        vectorRepository.getSimilarDocuments(question, topK = 5, threshold = threshold ?: 0.8)
          .takeIf { it.isNotEmpty() }
          ?: return "No relevant documents found for the question: $question"

      val context = relevantDocuments.joinToString("\n---\n") { it ?: "No content found" }

      val systemPrompt = """
          You are a helpful assistant. Use the following context to answer the user's question.
      
          Context:
          $context
      """.trimIndent()

      logger.info("User input: $question")
      val content: String? =
        chatClient.prompt().system(systemPrompt).user(question).call().content()
      logger.info("AI response: $content")
      return content
    } catch (e: Exception) {
      logger.error("Error during AI generation", e)
      return null
    }
  }
}