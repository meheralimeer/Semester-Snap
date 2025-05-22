package com.meher.semestersnap.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.meher.semestersnap.data.dao.AssignmentDao
import com.meher.semestersnap.data.dao.CourseDao
import com.meher.semestersnap.data.dao.QuizDao
import com.meher.semestersnap.data.entity.Assignment
import com.meher.semestersnap.data.entity.Course
import com.meher.semestersnap.data.entity.Quiz

@Database(
    entities = [Course::class, Assignment::class, Quiz::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun assignmentDao(): AssignmentDao
    abstract fun quizDao(): QuizDao
}