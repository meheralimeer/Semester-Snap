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

    suspend fun addCourse(course: Course): Long {
        return courseDao.insert(course)
    }

    fun updateCourse(course: Course) {
        viewModelScope.launch {
            courseDao.update(course)
        }
    }

    fun deleteCourseWithComponents(course: Course) {
        viewModelScope.launch {
            // Delete associated assignments and quizzes
            assignmentDao.getAssignmentsForCourse(course.id).first().forEach { assignment ->
                assignmentDao.delete(assignment)
            }
            quizDao.getQuizzesForCourse(course.id).first().forEach { quiz ->
                quizDao.delete(quiz)
            }
            // Delete the course
            courseDao.delete(course)
        }
    }

    fun addAssignment(courseId: Long) {
        viewModelScope.launch {
            assignmentDao.insert(Assignment(courseId = courseId, obtainedMarks = 0f, totalMarks = 10f))
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
            quizDao.insert(Quiz(courseId = courseId, obtainedMarks = 0f, totalMarks = 15f))
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

        var assignmentsCount = 0
        var quizCount = 0
        runBlocking {
            assignmentsFlow.first().forEach { assignment ->
                assignmentsCount++
            }
            quizzesFlow.first().forEach { quiz ->
                quizCount++
            }
        }

        var totalObtained = 0f

        val assignmentWeightage = 10f
        val quizWeightage = 15f
        val midtermWeightage = 25f
        val terminalWeightage = 50f

        val eachAssignmentWeightage = if (assignmentsCount > 0) assignmentWeightage / assignmentsCount else 0f
        var assignmentObtainedMarks = 0f

        val eachQuizWeightage = if (quizCount > 0) quizWeightage / quizCount else 0f
        var quizObtainedMarks = 0f

        runBlocking {
            courseFlow.first()?.let { course ->
                if (course.midtermObtained > 0 && course.midtermTotal > 0) {
                    totalObtained += (course.midtermObtained / course.midtermTotal) * midtermWeightage
                }
                if (course.terminalObtained > 0 && course.terminalTotal > 0) {
                    totalObtained += (course.terminalObtained / course.terminalTotal) * terminalWeightage
                }
            }
            assignmentsFlow.first().forEach { assignment ->
                if (assignment.obtainedMarks > 0 && assignment.totalMarks > 0) {
                    assignmentObtainedMarks += (assignment.obtainedMarks / assignment.totalMarks) * eachAssignmentWeightage
                }
            }
            quizzesFlow.first().forEach { quiz ->
                if (quiz.obtainedMarks > 0 && quiz.totalMarks > 0) {
                    quizObtainedMarks += (quiz.obtainedMarks / quiz.totalMarks) * eachQuizWeightage
                }
            }
        }

        totalObtained += assignmentObtainedMarks
        totalObtained += quizObtainedMarks

        val percentage = totalObtained
        return when {
            percentage >= 85 -> 4.00f
            percentage >= 80 -> 3.66f
            percentage >= 75 -> 3.33f
            percentage >= 71 -> 3.00f
            percentage >= 68 -> 2.66f
            percentage >= 64 -> 2.33f
            percentage >= 61 -> 2.00f
            percentage >= 58 -> 1.66f
            percentage >= 54 -> 1.33f
            percentage >= 50 -> 1.00f
            else -> 0.0f
        }
    }

    fun calculateCGPA(courses: List<Course>): Float {
        if (courses.isEmpty()) return 0f
        var totalGPA = 0f
        var totalCredits = 0
        courses.forEach { course ->
            totalGPA += (calculateGPA(course.id) * course.credits)
            totalCredits += course.credits
        }
        if (totalCredits == 0) return 0f
        return min(totalGPA / totalCredits, 4.0f)
    }
}