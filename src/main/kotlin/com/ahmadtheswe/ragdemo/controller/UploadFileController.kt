package com.ahmadtheswe.ragdemo.controller

import com.ahmadtheswe.ragdemo.dto.ResponseData
import com.ahmadtheswe.ragdemo.dto.UploadTextDto
import com.ahmadtheswe.ragdemo.service.VectorService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/upload")
class UploadFileController(private val vectorService: VectorService) {

  @PostMapping("/file")
  fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseData<String> {
    try {
      if (!file.originalFilename?.lowercase()?.endsWith(".txt")!!) {
        throw IllegalArgumentException("Only .txt files are allowed")
      }

      val content = String(file.bytes)
      vectorService.storeFileVectorData(content, file.originalFilename ?: "unknown")
      return ResponseData("File uploaded successfully")
    } catch (e: Exception) {
      return ResponseData(
        data = "Error: ${e.message}",
        message = "Failed to upload file"
      )
    }
  }

  @PostMapping("/text")
  fun uploadText(@RequestBody dto: UploadTextDto): ResponseData<String> {
    try {
      vectorService.storeTextVectorData(dto.text)
      return ResponseData("Text uploaded successfully")
    } catch (e: Exception) {
      return ResponseData(
        data = "Error: ${e.message}",
        message = "Failed to upload text"
      )
    }
  }

  @GetMapping("/similar")
  fun getSimilarDocuments(
    @RequestParam("query") query: String,
    @RequestParam("topK", required = false) topK: Int?,
    @RequestParam("threshold", required = false) threshold: Double?
  ): ResponseData<List<String?>> {
    try {
      val results = vectorService.getSimilarDocuments(query, topK, threshold)
      return ResponseData(results, "Retrieved similar documents successfully")
    } catch (e: Exception) {
      return ResponseData(
        data = emptyList(),
        message = "Failed to retrieve similar documents: ${e.message}"
      )
    }
  }
}