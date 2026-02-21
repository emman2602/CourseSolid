package com.example.coursesolid

interface StudentRepository {
    fun add(student: Student)
    fun getById(id: String): Student?
}