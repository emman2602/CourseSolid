package com.example.coursesolid

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EnrollmentServiceTest {

    private lateinit var enrollmentService: EnrollmentService
    private lateinit var courseRepository: InMemoryCourseRepository
    private lateinit var studentRepository: StudentRepository

    @Before
    fun setup() {
        courseRepository = InMemoryCourseRepository()

        // Stub for StudentRepository
        studentRepository = object : StudentRepository {
            private val students = mutableMapOf<String, Student>()

            override fun add(student: Student) {
                students[student.id] = student
            }

            override fun getById(id: String): Student? = students[id]
        }

        enrollmentService = EnrollmentService(courseRepository, studentRepository)
    }

    @Test
    fun `should throw exception when trying to enroll more students than capacity`() {
        // Arrange
        // We create a course with a maximum capacity of 2 for easier testing
        val course = Course("MATH101", "Basic Math", 2)
        courseRepository.add(course)

        val student1 = Student("1", "Alice")
        val student2 = Student("2", "Bob")
        val student3 = Student("3", "Charlie")

        studentRepository.add(student1)
        studentRepository.add(student2)
        studentRepository.add(student3)

        // Act
        enrollmentService.enroll(student1.id, course.code)
        enrollmentService.enroll(student2.id, course.code)

        // Assert
        val exception = assertThrows(IllegalStateException::class.java) {
            enrollmentService.enroll(student3.id, course.code) // Exceeds capacity
        }

        assertTrue(exception.message!!.contains("duplicated or full course"))
    }

    @Test
    fun `should throw exception when student tries to enroll twice in the same course`() {
        // Arrange
        val course = Course("HIST101", "History", 30)
        courseRepository.add(course)

        val student = Student("1", "Alice")
        studentRepository.add(student)

        // Act
        enrollmentService.enroll(student.id, course.code)

        // Assert
        val exception = assertThrows(IllegalStateException::class.java) {
            // Trying to enroll the same student again
            enrollmentService.enroll(student.id, course.code)
        }

        assertTrue(exception.message!!.contains("duplicated or full course"))
    }

    @Test
    fun `should establish correct relationships to show courses by student and students by course`() {
        // Arrange
        val course1 = Course("CS101", "Programming") // Uses default capacity 30
        val course2 = Course("CS102", "Data Structures")
        courseRepository.add(course1)
        courseRepository.add(course2)

        val student = Student("1", "Alice")
        studentRepository.add(student)

        // Act
        enrollmentService.enroll(student.id, course1.code)
        enrollmentService.enroll(student.id, course2.code)

        // Assert
        // Verify students by course
        val studentsInCourse1 = course1.getStudents()
        assertTrue("Course 1 should contain the student", studentsInCourse1.contains(student))

        // Verify courses by student
        val studentCourses = student.getCourse()
        assertEquals("Student should be enrolled in 2 courses", 2, studentCourses.size)
        assertTrue("Student should be enrolled in CS101", studentCourses.contains(course1))
        assertTrue("Student should be enrolled in CS102", studentCourses.contains(course2))
    }
}