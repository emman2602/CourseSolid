package com.example.coursesolid

interface TeacherRepository {
    fun add(teacher: Teacher)
    fun getByName(id: String): Teacher?
}