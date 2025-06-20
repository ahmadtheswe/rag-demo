package com.ahmadtheswe.ragdemo.controller

import com.ahmadtheswe.ragdemo.dto.ResponseData
import com.ahmadtheswe.ragdemo.service.RagService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(private val ragService: RagService) {

  private val logger = LoggerFactory.getLogger(ChatController::class.java)

  @GetMapping("/ai")
  fun generation(userInput: String): ResponseEntity<ResponseData<String>> {
    val response = ragService.ask(userInput)
    return if (response != null) {
      logger.info("AI response: $response")
      ResponseEntity.ok(ResponseData(response, "AI response generated successfully"))
    } else {
      logger.error("Failed to generate AI response")
      ResponseEntity.status(500)
        .body(ResponseData("Error generating AI response", "Failed to generate AI response"))
    }
  }
}