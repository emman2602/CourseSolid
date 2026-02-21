package com.example.coursesolid

class InMemoryTeacherRepository: TeacherRepository {
    private val teachers = mutableMapOf<String, Teacher>()
    override fun add(teacher: Teacher) {
        teachers[teacher.name] = teacher
    }

    override fun getByName(id: String): Teacher? = teachers[id]
}