package com.meher.semestersnap.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meher.semestersnap.data.dao.AssignmentDao
import com.meher.semestersnap.data.dao.CourseDao
import com.meher.semestersnap.data.dao.QuizDao
import com.meher.semestersnap.data.entity.Assignment
import com.meher.semestersnap.data.entity.Course
import com.meher.semestersnap.data.entity.Quiz
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.min

class GPAViewModel(
    private val courseDao: CourseDao,
    private val assignmentDao: AssignmentDao,
    private val quizDao: QuizDao
) : ViewModel() {

    val courses: Flow<List<Course>> = courseDao.getAllCourses()

    fun getCourse(courseId: Long): Flow<Course?> {
        return courseDao.getAllCourses().map { courses -> courses.find { it.id == courseId } }
    }

    fun getAssignmentsForCourse(courseId: Long): Flow<List<Assignment>> {
        return assignmentDao.getAssignmentsForCourse(courseId)
    }

    fun getQuizzesForCourse(courseId: Long): Flow<List<Quiz>> {
        return quizDao.getQuizzesForCourse(courseId)
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            courseDao.insert(course)
        }
    }

    fun updateCourse(course: Course) {
        viewModelScope.launch {
            courseDao.update(course)
        }
    }

    fun addAssignment(courseId: Long) {
        viewModelScope.launch {
            assignmentDao.insert(Assignment(courseId = courseId, obtainedMarks = 0f, totalMarks = 100f))
        }
    }

    fun updateAssignment(assignment: Assignment) {
        viewModelScope.launch {
            assignmentDao.update(assignment)
        }
    }

    fun deleteAssignment(assignment: Assignment) {
        viewModelScope.launch {
            assignmentDao.delete(assignment)
        }
    }

    fun addQuiz(courseId: Long) {
        viewModelScope.launch {
            quizDao.insert(Quiz(courseId = courseId, obtainedMarks = 0f, totalMarks = 50f))
        }
    }

    fun updateQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.update(quiz)
        }
    }

    fun deleteQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizDao.delete(quiz)
        }
    }

    fun calculateGPA(courseId: Long): Float {
        val courseFlow = getCourse(courseId)
        val assignmentsFlow = getAssignmentsForCourse(courseId)
        val quizzesFlow = getQuizzesForCourse(courseId)

        var totalObtained = 0f
        var totalMarks = 0f

        runBlocking {
            courseFlow.first()?.let { course ->
                totalObtained += course.midtermObtained + course.terminalObtained
                totalMarks += course.midtermTotal + course.terminalTotal
            }
            assignmentsFlow.first().forEach { assignment ->
                totalObtained += assignment.obtainedMarks
                totalMarks += assignment.totalMarks
            }
            quizzesFlow.first().forEach { quiz ->
                totalObtained += quiz.obtainedMarks
                totalMarks += quiz.totalMarks
            }
        }

        if (totalMarks == 0f) return 0f
        val percentage = (totalObtained / totalMarks) * 100
        return when {
            percentage >= 85 -> 4.0f
            percentage >= 80 -> 3.7f
            percentage >= 75 -> 3.3f
            percentage >= 70 -> 3.0f
            percentage >= 65 -> 2.7f
            percentage >= 60 -> 2.3f
            percentage >= 55 -> 2.0f
            percentage >= 50 -> 1.7f
            else -> 0.0f
        }
    }

    fun calculateCGPA(courses: List<Course>): Float {
        if (courses.isEmpty()) return 0f
        var totalGPA = 0f
        courses.forEach { course ->
            totalGPA += calculateGPA(course.id)
        }
        return min(totalGPA / courses.size, 4.0f)
    }
}