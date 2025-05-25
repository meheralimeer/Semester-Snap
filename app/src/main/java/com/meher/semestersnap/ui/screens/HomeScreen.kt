package com.meher.semestersnap.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.meher.semestersnap.ui.viewmodel.GPAViewModel

/*
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
}*/

@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(
    viewModel: GPAViewModel,
    onCourseClick: (Long) -> Unit,
    onAddCourse: () -> Unit
) {
    val courses = viewModel.courses.collectAsState(initial = emptyList())
    val cgpa = viewModel.calculateCGPA(courses.value)

    val primaryColor = MaterialTheme.colorScheme.primary
    val bgColor = Color.Black
    val textColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "Semester Snapshot",
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CGPA Card styled like Samsung UI
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Your CGPA",
                    color = textColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = String.format("%.2f / 4.0", cgpa),
                    color = textColor,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = (cgpa / 4f).coerceIn(0f, 1f),
                    color = primaryColor,
                    trackColor = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Course Cards
        LazyColumn {
            items(courses.value) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onCourseClick(course.id) },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            course.name,
                            color = textColor,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = String.format("GPA: %.2f", viewModel.calculateGPA(course.id)),
                            color = textColor,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        // FAB
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = onAddCourse,
                containerColor = primaryColor,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Course")
            }
        }
    }
}

