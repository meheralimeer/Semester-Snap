package com.meher.semestersnap.model

class Semester(val name: String) {
    val assignments = mutableListOf<Assignment>()
    val quizzes = mutableListOf<Quizz>()
    val midTerm: Mid = Mid("Mid Term", 0, 0)
    val terminal: Terminal = Terminal("Terminal", 0, 0)

    fun addAssignment(name: String, totalMarks: Int, obtainedMarks: Int) {
        assignments.add(Assignment(name, totalMarks, obtainedMarks))
    }

    fun addQuizz(name: String, totalMarks: Int, obtainedMarks: Int) {
        quizzes.add(Quizz(name, totalMarks, obtainedMarks))
    }

    fun updateMidTerm(name: String, totalMarks: Int, obtainedMarks: Int) {
        midTerm.name = name
        midTerm.totalMarks = totalMarks
        midTerm.obtainedMarks = obtainedMarks
    }

    fun updateTerminal(name: String, totalMarks: Int, obtainedMarks: Int) {
        terminal.name = name
        terminal.totalMarks = totalMarks
        terminal.obtainedMarks = obtainedMarks
    }

    fun updateAssignmentByName(name: String, totalMarks: Int, obtainedMarks: Int) {
        assignments.find { it.name == name }?.let {
            it.totalMarks = totalMarks
            it.obtainedMarks = obtainedMarks
        }
    }

    fun updateQuizzByName(name: String, totalMarks: Int, obtainedMarks: Int) {
        quizzes.find { it.name == name }?.let {
            it.totalMarks = totalMarks
            it.obtainedMarks = obtainedMarks
        }
    }
}