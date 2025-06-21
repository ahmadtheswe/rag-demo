package com.ahmadtheswe.ragdemo.controller

import com.ahmadtheswe.ragdemo.dto.ChatRequest
import com.ahmadtheswe.ragdemo.dto.ResponseData
import com.ahmadtheswe.ragdemo.service.RagService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/chat")
class ChatController(private val ragService: RagService) {

  private val logger = LoggerFactory.getLogger(ChatController::class.java)

  @PostMapping("/ask")
  fun generation(@RequestBody dto: ChatRequest): ResponseEntity<ResponseData<String>> {
    if (dto.question.isBlank()) {
      logger.error("Received empty question")
      return ResponseEntity.badRequest()
        .body(ResponseData("Question cannot be empty", "Invalid request"))
    }

    val response = ragService.ask(dto.question, dto.threshold)
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