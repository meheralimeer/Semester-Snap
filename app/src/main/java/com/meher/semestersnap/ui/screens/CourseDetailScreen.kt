package com.meher.semestersnap.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.meher.semestersnap.data.entity.Course
import com.meher.semestersnap.ui.viewmodel.GPAViewModel

@Composable
fun CourseDetailScreen(
    viewModel: GPAViewModel,
    courseId: Long,
    onBack: () -> Unit
) {
    val course by viewModel.getCourse(courseId).collectAsState(initial = null)
    val assignments by viewModel.getAssignmentsForCourse(courseId).collectAsState(initial = emptyList())
    val quizzes by viewModel.getQuizzesForCourse(courseId).collectAsState(initial = emptyList())

    var courseName by remember { mutableStateOf("") }
    var credits by remember { mutableStateOf("") }
    var midtermObtained by remember { mutableStateOf("") }
    var midtermTotal by remember { mutableStateOf("") }
    var terminalObtained by remember { mutableStateOf("") }
    var terminalTotal by remember { mutableStateOf("") }

    LaunchedEffect(course) {
        course?.let {
            courseName = it.name
            credits = it.credits.toString()
            midtermObtained = it.midtermObtained.toString()
            midtermTotal = it.midtermTotal.toString()
            terminalObtained = it.terminalObtained.toString()
            terminalTotal = it.terminalTotal.toString()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = courseName,
            onValueChange = { courseName = it },
            label = { Text("Course Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = credits,
            onValueChange = {credits = it},
            label = { Text("Credit Hours")},
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text("Midterm Exam", style = MaterialTheme.typography.titleMedium)
        Row {
            OutlinedTextField(
                value = midtermObtained,
                onValueChange = { midtermObtained = it },
                label = { Text("Obtained") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = midtermTotal,
                onValueChange = { midtermTotal = it },
                label = { Text("Total") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Terminal Exam", style = MaterialTheme.typography.titleMedium)
        Row {
            OutlinedTextField(
                value = terminalObtained,
                onValueChange = { terminalObtained = it },
                label = { Text("Obtained") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                value = terminalTotal,
                onValueChange = { terminalTotal = it },
                label = { Text("Total") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Assignments", style = MaterialTheme.typography.titleMedium)
        assignments.forEachIndexed { index, assignment ->
            Row {
                OutlinedTextField(
                    value = assignment.obtainedMarks.toString(),
                    onValueChange = {
                        viewModel.updateAssignment(
                            assignment.copy(obtainedMarks = it.toFloatOrNull() ?: 0f)
                        )
                    },
                    label = { Text("Assignment ${index + 1} Obtained") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = assignment.totalMarks.toString(),
                    onValueChange = {
                        viewModel.updateAssignment(
                            assignment.copy(totalMarks = it.toFloatOrNull() ?: 0f)
                        )
                    },
                    label = { Text("Total") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { viewModel.deleteAssignment(assignment) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Assignment")
                }
            }
        }
        Button(onClick = { viewModel.addAssignment(courseId) }) {
            Text("Add Assignment")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Quizzes", style = MaterialTheme.typography.titleMedium)
        quizzes.forEachIndexed { index, quiz ->
            Row {
                OutlinedTextField(
                    value = quiz.obtainedMarks.toString(),
                    onValueChange = {
                        viewModel.updateQuiz(
                            quiz.copy(obtainedMarks = it.toFloatOrNull() ?: 0f)
                        )
                    },
                    label = { Text("Quiz ${index + 1} Obtained") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = quiz.totalMarks.toString(),
                    onValueChange = {
                        viewModel.updateQuiz(
                            quiz.copy(totalMarks = it.toFloatOrNull() ?: 0f)
                        )
                    },
                    label = { Text("Total") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { viewModel.deleteQuiz(quiz) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Quiz")
                }
            }
        }
        Button(onClick = { viewModel.addQuiz(courseId) }) {
            Text("Add Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(
                onClick = {
                    val updatedCourse = Course(
                        id = courseId,
                        name = courseName,
                        credits = credits.toIntOrNull() ?: 0,
                        midtermObtained = midtermObtained.toFloatOrNull() ?: 0f,
                        midtermTotal = midtermTotal.toFloatOrNull() ?: 0f,
                        terminalObtained = terminalObtained.toFloatOrNull() ?: 0f,
                        terminalTotal = terminalTotal.toFloatOrNull() ?: 0f
                    )
                    if (courseId == 0L) {
                        viewModel.addCourse(updatedCourse)
                    } else {
                        viewModel.updateCourse(updatedCourse)
                    }
                    onBack()
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancel")
            }
        }
    }
}