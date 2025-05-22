package com.meher.semestersnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meher.semestersnap.ui.theme.GPACalculatorTheme
import com.meher.semestersnap.ui.screens.CourseDetailScreen
import com.meher.semestersnap.ui.screens.HomeScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GPACalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(
                                viewModel = koinViewModel(),
                                onCourseClick = { courseId ->
                                    navController.navigate("course_detail/$courseId")
                                },
                                onAddCourse = {
                                    navController.navigate("course_detail/0")
                                }
                            )
                        }
                        composable("course_detail/{courseId}") { backStackEntry ->
                            val courseId = backStackEntry.arguments?.getString("courseId")?.toLongOrNull() ?: 0L
                            CourseDetailScreen(
                                viewModel = koinViewModel(),
                                courseId = courseId,
                                onBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}