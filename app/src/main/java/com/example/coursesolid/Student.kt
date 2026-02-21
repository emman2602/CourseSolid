package com.example.coursesolid
 class Student( val id: String,
                    val name: String){
     private val courses = mutableSetOf<Course>()

     fun enroll(course: Course){
         courses.add(course)
     }

     fun getCourse(): Set<Course> = courses
 }
