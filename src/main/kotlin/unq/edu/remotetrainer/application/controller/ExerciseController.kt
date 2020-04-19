package unq.edu.remotetrainer.application.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ExerciseController {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }
}