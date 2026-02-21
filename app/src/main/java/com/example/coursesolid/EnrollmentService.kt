package com.example.coursesolid

class EnrollmentService(
    private val courseRepository: CourseRepository,
    private val studentRepository: StudentRepository
) {
    fun enroll (studentId: String, courseCode: String) {
        val student = studentRepository.getById(studentId)
            ?: throw IllegalArgumentException("Student not found")

        val course = courseRepository.getByCode((courseCode))
            ?: throw IllegalArgumentException("Course not found")

        if(!course.enrollStudent(student)){
            throw IllegalStateException("Enrollment failed: duplicated or full course")
        }

    }

    fun showCoursesByStudent(student: Student){
        println("    Cursos de ${student.name}")
        student.getCourse().forEach {
            println("- ${it.description}")
        }
    }

    fun showStudentsByCourse(course: Course){
        println("  Curso: ${course.description}")
        course.getStudents().forEach {
            println("- ${it.name}")
        }
    }
}