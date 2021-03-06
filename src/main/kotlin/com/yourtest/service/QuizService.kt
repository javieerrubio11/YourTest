package com.yourtest.service

import com.yourtest.entity.Quiz

interface QuizService {
    fun getAll() : List<Quiz>
    fun getOne(id : Long) : Quiz
    fun insert(quiz : Quiz) : Quiz
    fun delete(id : Long)
    fun findByOwnerUsername(ownerUsername : String) : List<Quiz>
}