# Semester Snap - GPA Calculator

**Semester Snap** is a modern Android application built with Jetpack Compose to help students calculate their GPA and CGPA by managing courses, assignments, quizzes, and exams. With an intuitive interface inspired by Samsung's UI, it simplifies academic performance tracking.

<p align="center">
  <img src="https://github.com/user-attachments/assets/8ad94b2f-2e56-401f-baf6-3f302cbb3548" alt="Semester Snap Logo">
</p>

## Features

- **Course Management**: Add, edit, and delete courses with details like name, credit hours, midterm, and terminal exam scores.
- **Assignments & Quizzes**: Track assignments and quizzes for each course with obtained and total marks.
- **GPA Calculation**: Automatically calculate GPA for each course based on assignments (10%), quizzes (15%), midterm (25%), and terminal exams (50%).
- **CGPA Tracking**: Compute cumulative GPA (CGPA) across all courses, displayed with a progress bar.
- **Interactive UI**: Long-press to delete courses with a confirmation dialog, and a floating action button to add new courses.
- **Material 3 Design**: Clean, responsive design with a consistent theme using Material Design 3 components.
- **Room Database**: Persistent storage for courses, assignments, and quizzes using Room.

## Screenshots

| Home Screen | Course Details |
|-------------|----------------|
| ![Screenshot_20250531-112342_Semester Snap](https://github.com/user-attachments/assets/ad965196-42ea-4688-a973-fea312e98593) | ![Screenshot_20250531-112349_Semester Snap](https://github.com/user-attachments/assets/c9dc0d8e-499b-483c-9809-476eda474385) |

## Installation

### Prerequisites
- Android device or emulator running **Android 7.0 (API 24)** or higher.
- Android Studio (latest stable version recommended) for building from source.
- Git installed for cloning the repository.

### Download APK
1. Visit the [Releases](https://github.com/meheralimeer/semester-snap/releases) page.
2. Download the latest release APK.
3. Enable **Install from Unknown Sources** on your Android device.
4. Install the APK and launch **Semester Snap**.

### Build from Source
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/meheralimeer/semester-snap.git
   cd semestersnap
   ```
2. **Open in Android Studio**:
   - Open Android Studio and select **Open an existing project**.
   - Navigate to the cloned `semester snap` directory and open it.
3. **Sync Project**:
   - Sync the project with Gradle by clicking **Sync Project with Gradle Files**.
4. **Configure Signing** (for release builds):
   - Create a keystore file if you don’t have one:
     ```bash
     keytool -genkey -v -keystore my_keystore.jks -alias my_alias -keyalg RSA -keysize 2048 -validity 10000
     ```
   - Update `app/build.gradle` with signing configurations:
     ```gradle
     android {
         ...
         signingConfigs {
             release {
                 storeFile file('path/to/my_keystore.jks')
                 storePassword 'your_store_password'
                 keyAlias 'my_alias'
                 keyPassword 'your_key_password'
             }
         }
         buildTypes {
             release {
                 signingConfig signingConfigs.release
                 ...
             }
         }
     }
     ```
5. **Build and Run**:
   - Build the project (`Build > Make Project`).
   - Run on an emulator or connected device (`Run > Run 'app'`).

## Usage

1. **Home Screen**:
   - View your CGPA with a progress bar.
   - See a list of courses with their GPAs.
   - Long-press a course to delete it (confirmation dialog appears).
   - Click the **+** FAB to add a new course.

2. **Course Details**:
   - Enter course details (name, credits).
   - Add midterm and terminal exam scores (obtained/total).
   - Add, edit, or delete assignments and quizzes with their marks.
   - Save changes or cancel (new courses are deleted on cancel to avoid orphaned data).

3. **GPA and CGPA**:
   - GPA is calculated per course based on:
     - Assignments: 10% (evenly distributed among assignments).
     - Quizzes: 15% (evenly distributed among quizzes).
     - Midterm: 25%.
     - Terminal: 50%.
   - CGPA is the credit-weighted average of all course GPAs, capped at 4.0.

## Project Structure

- **app/src/main/java/com/meher/semestersnap**:
  - **data**: Room database entities (`Course`, `Assignment`, `Quiz`) and DAOs.
  - **ui**:
    - **screens**: Composable screens (`HomeScreen`, `CourseDetailScreen`).
    - **theme**: Material 3 theme (`Theme.kt`, `colors.xml`).
    - **viewmodel**: `GPAViewModel` for business logic.
  - **MainActivity.kt**: Entry point with navigation setup.
- **app/src/main/res**: Resources (drawables, icons, colors).
- **.github/workflows**: GitHub Actions for automated APK builds and releases.

## Contributing

Contributions are welcome! Follow these steps:

1. **Fork the Repository**:
   Click **Fork** on the GitHub page.
2. **Create a Branch**:
   ```bash
   git checkout -b feature/your-feature
   ```
3. **Make Changes**:
   Implement your feature or bug fix.
4. **Commit and Push**:
   ```bash
   git add .
   git commit -m "Add your-feature"
   git push origin feature/your-feature
   ```
5. **Create a Pull Request**:
   Open a pull request on GitHub, describing your changes.

### Guidelines
- Follow Kotlin and Jetpack Compose best practices.
- Use Material Design 3 components for UI consistency.
- Write clear commit messages and document changes.
- Test your changes on an emulator or device.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Credits

Developed by [Meher Ali/@meheralimeer]. Built with:
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Room](https://developer.android.com/training/data-storage/room)
- [Material 3](https://m3.material.io/)
- [Koin](https://insert-koin.io/) for dependency injection

## Contact

For issues or suggestions, open an [issue](https://github.com/meheralimeer/semester-snap/issues) or contact [meherali.meer@gmail.com].

---

*Happy studying with SemesterSnap!*
