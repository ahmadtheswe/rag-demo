package com.ahmadtheswe.ragdemo.controller

import com.ahmadtheswe.ragdemo.dto.ResponseData
import com.ahmadtheswe.ragdemo.dto.UploadTextDto
import com.ahmadtheswe.ragdemo.service.VectorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/upload")
class UploadFileController(private val vectorService: VectorService) {

  @PostMapping("/file")
  fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ResponseData<String>> {
    try {
      vectorService.storeFileVectorData(file)
      return ResponseEntity.ok(
        ResponseData("File uploaded successfully", "File processed and stored")
      )
    } catch (e: Exception) {
      return ResponseEntity.status(500).body(
        ResponseData(
          data = "Error: ${e.message}", message = "Failed to upload file"
        )
      )
    }
  }

  @PostMapping("/text")
  fun uploadText(@RequestBody dto: UploadTextDto): ResponseEntity<ResponseData<String>> {
    try {
      vectorService.storeTextVectorData(dto.text)
      return ResponseEntity.ok(
        ResponseData("Text uploaded successfully", "Text processed and stored")
      )
    } catch (e: Exception) {
      return ResponseEntity.status(500).body(
        ResponseData(
          data = "Error: ${e.message}", message = "Failed to upload text"
        )
      )
    }
  }

  @GetMapping("/similar")
  fun getSimilarDocuments(
    @RequestParam("query") query: String,
    @RequestParam("topK", required = false) topK: Int?,
    @RequestParam("threshold", required = false) threshold: Double?
  ): ResponseEntity<ResponseData<List<String?>>> {
    try {
      val results = vectorService.getSimilarDocuments(query, topK, threshold)
      return ResponseEntity.ok(
        ResponseData(results, "Retrieved similar documents successfully")
      )
    } catch (e: Exception) {
      return ResponseEntity.status(500).body(
        ResponseData(
          data = emptyList<String>(), message = "Failed to retrieve similar documents: ${e.message}"
        )
      )
    }
  }
}