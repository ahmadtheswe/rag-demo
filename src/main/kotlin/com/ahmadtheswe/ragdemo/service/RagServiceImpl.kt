package com.ahmadtheswe.ragdemo.service

import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Service

@Service
class RagServiceImpl(chatClientBuilder: ChatClient.Builder) : RagService {
  private val logger = LoggerFactory.getLogger(RagServiceImpl::class.java)
  private val chatClient: ChatClient = chatClientBuilder.build()

  override fun ask(question: String): String? {
    try {
      logger.info("User input: $question")
      val content: String? = chatClient.prompt()
        .user(question)
        .call()
        .content()
      logger.info("AI response: $content")
      return content
    } catch (e: Exception) {
      logger.error("Error during AI generation", e)
      return null
    }
  }
}