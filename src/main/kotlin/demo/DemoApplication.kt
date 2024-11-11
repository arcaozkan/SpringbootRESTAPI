package demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.cache.annotation.EnableCaching

@EnableScheduling
@SpringBootApplication
@EnableCaching
class DemoApplication

fun main(args: Array<String>) {
    runApplication<DemoApplication>(*args)
}
