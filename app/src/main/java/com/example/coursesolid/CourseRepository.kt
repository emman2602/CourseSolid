package com.example.coursesolid

interface CourseRepository {
    fun add(course: Course)
    fun getByCode(code: String):Course?
    fun getAll(): List<Course>
}