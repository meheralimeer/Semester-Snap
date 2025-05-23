package com.meher.semestersnap.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.meher.semestersnap.ui.viewmodel.GPAViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import kotlinx.coroutines.FlowPreview

@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    viewModel: GPAViewModel,
    onCourseClick: (Long) -> Unit,
    onAddCourse: () -> Unit
) {
    val courses = viewModel.courses.collectAsState(initial = emptyList())
    val cgpa = viewModel.calculateCGPA(courses.value)

    // ****** Read the theme color here ******
    val primaryColor = MaterialTheme.colorScheme.primary

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Circular Progress Bar for CGPA
        Box(
            modifier = Modifier
                .size(150.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                val strokeWidth = 20f
                val progress = cgpa / 4.0f // Assuming 4.0 scale

                // Background circle
                drawArc(
                    color = Color.Gray, // Or use another theme color if desired, e.g., MaterialTheme.colorScheme.surfaceVariant
                    startAngle = -90f,
                    sweepAngle = 360f,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(canvasWidth - strokeWidth, canvasHeight - strokeWidth),
                    style = Stroke(width = strokeWidth)
                )

                // Progress arc
                drawArc(
                    color = primaryColor, // ****** Use the fetched color here ******
                    startAngle = -90f,
                    sweepAngle = 360f * progress,
                    useCenter = false,
                    topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                    size = Size(canvasWidth - strokeWidth, canvasHeight - strokeWidth),
                    style = Stroke(width = strokeWidth)
                )
            }
            Text(
                text = String.format("%.2f", cgpa),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Course List
        LazyColumn {
            items(courses.value) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onCourseClick(course.id) }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            course.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        // Assuming calculateGPA is not a composable and returns a simple type
                        Text(
                            text = String.format("GPA: %.2f", viewModel.calculateGPA(course.id)),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = onAddCourse,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Course")
        }
    }
}