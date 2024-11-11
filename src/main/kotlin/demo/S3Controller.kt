package demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.core.sync.RequestBody
import java.nio.file.Files
import java.nio.file.Path

@RestController
@RequestMapping("/s3")
class S3Controller(@Autowired private val s3Client: S3Client) {

    private val bucketName = "clicker-test-bucket" // replace with your bucket name

    @PostMapping("/upload")
    fun uploadFile(@RequestParam file: MultipartFile): ResponseEntity<String> {
        val objectKey = file.originalFilename ?: "default-name"

        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build()

        s3Client.putObject(request, RequestBody.fromInputStream(file.inputStream, file.size))
        return ResponseEntity.ok("File uploaded successfully: $objectKey")
    }

    @GetMapping("/download/{objectKey}")
    fun downloadFile(@PathVariable objectKey: String): ResponseEntity<ByteArray> {
        val request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build()

        val s3Object = s3Client.getObject(request)

        val tempFilePath = Files.createTempFile(objectKey, null)
        Files.write(tempFilePath, s3Object.readAllBytes())
        return ResponseEntity.ok(Files.readAllBytes(tempFilePath))
    }
}
