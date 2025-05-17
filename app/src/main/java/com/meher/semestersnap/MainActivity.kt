package com.meher.semestersnap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.meher.semestersnap.ui.theme.SemesterSnapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SemesterSnapTheme {
                SemesterSnap()
            }
        }
    }
}

@Composable
fun SemesterSnap(modifier: Modifier = Modifier) {

}

@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Te
}

