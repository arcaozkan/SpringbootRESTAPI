package demo
import org.springframework.web.bind.annotation.*
import org.springframework.scheduling.annotation.Scheduled
import software.amazon.awssdk.services.s3.S3Client

@RestController
@RequestMapping("/buckets")
class BucketController(
    private val s3Client: S3Client
) {
    @GetMapping
    fun listBuckets(): List<String> {
        val response = s3Client.listBuckets()
        return response.buckets()
            .mapIndexed { index, bucket ->
                "Bucket #${index + 1}: ${bucket.name()}"
            }
    }
}