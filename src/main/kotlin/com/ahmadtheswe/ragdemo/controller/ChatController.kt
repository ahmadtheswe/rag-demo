package com.ahmadtheswe.ragdemo.controller

import com.ahmadtheswe.ragdemo.dto.ResponseData
import org.slf4j.LoggerFactory
import org.springframework.ai.chat.client.ChatClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(chatClientBuilder: ChatClient.Builder) {

  private val logger = LoggerFactory.getLogger(ChatController::class.java)
  private val chatClient: ChatClient = chatClientBuilder.build()

  @GetMapping("/health")
  fun healthCheck(): ResponseData<String> {
    return ResponseData("ok")
  }

  @GetMapping("/ai")
  fun generation(userInput: String): ResponseData<String> {
    try {
      logger.info("User input: $userInput")
      val content: String? = chatClient.prompt()
        .user(userInput)
        .call()
        .content()
      logger.info("AI response: $content")
      return ResponseData(
        data = content ?: "No response from AI",
        message = "AI response generated successfully"
      )
    } catch (e: Exception) {
      logger.error("Error during AI generation", e)
      return ResponseData(
        data = "Error: ${e.message}",
        message = "Failed to generate AI response"
      )
    }
  }
}