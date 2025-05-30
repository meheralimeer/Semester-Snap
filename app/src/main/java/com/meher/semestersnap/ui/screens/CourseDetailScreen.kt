package com.meher.semestersnap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.meher.semestersnap.data.entity.Course
import com.meher.semestersnap.ui.viewmodel.GPAViewModel
import kotlinx.coroutines.launch

@Composable
fun CourseDetailScreen(
    viewModel: GPAViewModel,
    courseId: Long,
    onBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var courseID by remember { mutableLongStateOf(courseId) }
    val course by viewModel.getCourse(courseID).collectAsState(initial = null)
    val assignments by viewModel.getAssignmentsForCourse(courseID).collectAsState(initial = emptyList())
    val quizzes by viewModel.getQuizzesForCourse(courseID).collectAsState(initial = emptyList())

    var courseName by remember { mutableStateOf("") }
    var credits by remember { mutableStateOf("") }
    var midtermObtained by remember { mutableStateOf("") }
    var midtermTotal by remember { mutableStateOf("") }
    var terminalObtained by remember { mutableStateOf("") }
    var terminalTotal by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    // Insert a new course immediately if courseId is 0L
    LaunchedEffect(courseId) {
        if (courseId == 0L) {
            val newCourse = Course(
                id = 0L,
                name = "",
                credits = 0,
                midtermObtained = 0f,
                midtermTotal = 0f,
                terminalObtained = 0f,
                terminalTotal = 0f
            )
            courseID = viewModel.addCourse(newCourse)
        }
    }

    // Update UI fields when course data is loaded
    LaunchedEffect(course) {
        course?.let {
            courseID = it.id
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
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Edit Course", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            StyledCard("Course Information") {
                StyledTextField("Course Name", courseName) { courseName = it }
                Spacer(modifier = Modifier.height(8.dp))
                StyledTextField("Credit Hours", credits, KeyboardType.Number) { credits = it }
            }

            Spacer(modifier = Modifier.height(12.dp))

            StyledCard("Midterm Exam") {
                Row {
                    StyledTextField(
                        "Obtained",
                        midtermObtained,
                        KeyboardType.Number,
                        Modifier.weight(1f)
                    ) {
                        midtermObtained = it
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    StyledTextField(
                        "Total",
                        midtermTotal,
                        KeyboardType.Number,
                        Modifier.weight(1f)
                    ) {
                        midtermTotal = it
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            StyledCard("Terminal Exam") {
                Row {
                    StyledTextField(
                        "Obtained",
                        terminalObtained,
                        KeyboardType.Number,
                        Modifier.weight(1f)
                    ) {
                        terminalObtained = it
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    StyledTextField(
                        "Total",
                        terminalTotal,
                        KeyboardType.Number,
                        Modifier.weight(1f)
                    ) {
                        terminalTotal = it
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            StyledCard("Assignments") {
                assignments.forEachIndexed { index, assignment ->
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StyledTextField(
                                "Assignment ${index + 1} Obtained",
                                assignment.obtainedMarks.toString(),
                                KeyboardType.Number,
                                Modifier.weight(1f)
                            ) {
                                viewModel.updateAssignment(
                                    assignment.copy(obtainedMarks = it.toFloatOrNull() ?: 0f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            StyledTextField(
                                "Total",
                                assignment.totalMarks.toString(),
                                KeyboardType.Number,
                                Modifier.weight(1f)
                            ) {
                                viewModel.updateAssignment(
                                    assignment.copy(totalMarks = it.toFloatOrNull() ?: 0f)
                                )
                            }
                            IconButton(onClick = { viewModel.deleteAssignment(assignment) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.addAssignment(courseID) }) {
                    Text("Add Assignment")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            StyledCard("Quizzes") {
                quizzes.forEachIndexed { index, quiz ->
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            StyledTextField(
                                "Quiz ${index + 1} Obtained",
                                quiz.obtainedMarks.toString(),
                                KeyboardType.Number,
                                Modifier.weight(1f)
                            ) {
                                viewModel.updateQuiz(
                                    quiz.copy(obtainedMarks = it.toFloatOrNull() ?: 0f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            StyledTextField(
                                "Total",
                                quiz.totalMarks.toString(),
                                KeyboardType.Number,
                                Modifier.weight(1f)
                            ) {
                                viewModel.updateQuiz(
                                    quiz.copy(totalMarks = it.toFloatOrNull() ?: 0f)
                                )
                            }
                            IconButton(onClick = { viewModel.deleteQuiz(quiz) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { viewModel.addQuiz(courseID) }) {
                    Text("Add Quiz")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = {
                        val updatedCourse = Course(
                            id = courseID,
                            name = courseName,
                            credits = credits.toIntOrNull() ?: 0,
                            midtermObtained = midtermObtained.toFloatOrNull() ?: 0f,
                            midtermTotal = midtermTotal.toFloatOrNull() ?: 0f,
                            terminalObtained = terminalObtained.toFloatOrNull() ?: 0f,
                            terminalTotal = terminalTotal.toFloatOrNull() ?: 0f
                        )
                        coroutineScope.launch {
                            viewModel.updateCourse(updatedCourse)
                            onBack()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if (courseId == 0L) {
                            // Delete the temporary course if canceling a new course
                            coroutineScope.launch {
                                course?.let { viewModel.deleteCourseWithComponents(it) }
                                onBack()
                            }
                        } else {
                            onBack()
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}

@Composable
fun StyledCard(title: String? = null, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            title?.let {
                Text(text = it, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(12.dp))
            }
            content()
        }
    }
}

@Composable
fun StyledTextField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = modifier.fillMaxWidth()
    )
}