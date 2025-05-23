package com.meher.semestersnap.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val credits: Int,
    val midtermObtained: Float,
    val midtermTotal: Float,
    val terminalObtained: Float,
    val terminalTotal: Float
)