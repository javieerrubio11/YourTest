package com.yourtest.controller

import com.yourtest.entity.Question
import com.yourtest.entity.QuestionDto
import com.yourtest.service.QuestionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/question")
@CrossOrigin("*")
class QuestionController {
    @Autowired
    lateinit var questionService: QuestionService

    @GetMapping(value = "/all")
    fun getAll() = questionService.getAll()

    @GetMapping(value = "/get/{id}")
    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    fun get(@PathVariable id: Long) : Question {
        return questionService.getOne(id)
    }

    @PostMapping(value = "/insert")
    fun insert(@RequestBody questionDto: QuestionDto) : List<Question> {
        questionService.insert(Question(questionDto))
        return questionService.getAll()
    }

    @GetMapping(value = "/findByQuiz/{quizId}")
    fun getByQuizId(@PathVariable quizId: Long) : List<Question> {
        return questionService.listByQuizId(quizId)
    }

    @GetMapping(value = "/find/{nombre}")
//    @PreAuthorize("hasAuthority('ADMIN_USER')")
//    @PreAuthorize("hasAuthority('ADMIN_USER') or hasAuthority('STANDARD_USER')")
    fun getByNombre(@PathVariable nombre: String) : List<Question> {
        return questionService.listByName(nombre)
    }

    @DeleteMapping(value = "/{id}")
    fun delete(@PathVariable id: Long) : List<Question> {
        var question = questionService.getOne(id);
        var quizId: Long? = question?.quiz?.id;
        try {
            questionService.delete(id)
            if(quizId != null)
                return questionService.listByQuizId(quizId)
            else
                return emptyList();
        } catch (e : Exception) {
            e.printStackTrace()
            throw Exception(e.localizedMessage)
        }
    }
}