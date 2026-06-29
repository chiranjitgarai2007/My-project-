package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.data.model.Course
import com.example.data.model.CourseCategory
import com.example.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseRepository {
    private const val TAG = "FirebaseRepository"

    private var auth: FirebaseAuth? = null
    private var firestore: FirebaseFirestore? = null
    private var isFirebaseAvailable = false

    // Fallback simulated local database state
    private var localCurrentUser: User? = null
    private val localUsers = mutableMapOf<String, User>()
    private val localCategories = mutableListOf<CourseCategory>()
    private val localCourses = mutableListOf<Course>()

    init {
        try {
            // Attempt to initialize Firebase services.
            // If google-services.json is missing or Firebase is not initialized, this throws.
            auth = FirebaseAuth.getInstance()
            firestore = FirebaseFirestore.getInstance()
            isFirebaseAvailable = true
            Log.d(TAG, "Firebase successfully initialized.")
        } catch (e: Exception) {
            isFirebaseAvailable = false
            Log.w(TAG, "Firebase not initialized or google-services.json missing. Using local secure fallback engine. Error: ${e.localizedMessage}")
        }

        // Initialize mock static content representing the Firestore data requested
        initializeStaticData()
    }

    private fun initializeStaticData() {
        // courseCategories documents: mobile_skills, pc_skills
        localCategories.add(
            CourseCategory(
                id = "mobile_skills",
                name = "Mobile Skills",
                iconName = "smartphone",
                description = "Master top digital skills directly on your mobile device."
            )
        )
        localCategories.add(
            CourseCategory(
                id = "pc_skills",
                name = "Laptop / PC Skills",
                iconName = "desktop",
                description = "Unlock professional capabilities with high-performance desktop tools."
            )
        )

        // Courses for Category 1: Mobile Skills
        localCourses.add(
            Course(
                id = "m1",
                categoryId = "mobile_skills",
                title = "Video Editing",
                description = "Learn cinematic editing using CapCut and VN Editor with just your smartphone.",
                duration = "8 Weeks",
                lessonsCount = 24,
                rating = 4.9,
                instructor = "Arjun Sen",
                iconName = "movie"
            )
        )
        localCourses.add(
            Course(
                id = "m2",
                categoryId = "mobile_skills",
                title = "AI Tools",
                description = "Leverage advanced mobile AI tools for content writing, voice generation, and art creation.",
                duration = "6 Weeks",
                lessonsCount = 18,
                rating = 4.8,
                instructor = "Dr. Kabir Roy",
                iconName = "psychology"
            )
        )
        localCourses.add(
            Course(
                id = "m3",
                categoryId = "mobile_skills",
                title = "YouTube Growth",
                description = "Complete roadmap to edit, upload, optimize, and monetize your YouTube channel on mobile.",
                duration = "10 Weeks",
                lessonsCount = 35,
                rating = 4.9,
                instructor = "Rohit Verma",
                iconName = "play_circle"
            )
        )
        localCourses.add(
            Course(
                id = "m4",
                categoryId = "mobile_skills",
                title = "Instagram & Social Media",
                description = "Master short-form reels, algorithm hacks, brand collaborations, and design tools.",
                duration = "5 Weeks",
                lessonsCount = 15,
                rating = 4.7,
                instructor = "Simran Kaur",
                iconName = "share"
            )
        )
        localCourses.add(
            Course(
                id = "m5",
                categoryId = "mobile_skills",
                title = "Graphics Design",
                description = "Design professional flyers, banners, and logos using Canva and Pixellab mobile.",
                duration = "7 Weeks",
                lessonsCount = 20,
                rating = 4.8,
                instructor = "Nisha Patel",
                iconName = "brush"
            )
        )

        // Courses for Category 2: Laptop / PC Skills
        localCourses.add(
            Course(
                id = "p1",
                categoryId = "pc_skills",
                title = "Video Editing",
                description = "Professional video editing using Premiere Pro and After Effects for stunning desktop renders.",
                duration = "12 Weeks",
                lessonsCount = 45,
                rating = 4.9,
                instructor = "Vikram Aditya",
                iconName = "movie_filter"
            )
        )
        localCourses.add(
            Course(
                id = "p2",
                categoryId = "pc_skills",
                title = "Web Development",
                description = "Full stack developer guide including HTML/CSS, Javascript, React, Node.js and SQL.",
                duration = "16 Weeks",
                lessonsCount = 80,
                rating = 4.9,
                instructor = "Amit Sharma",
                iconName = "code"
            )
        )
        localCourses.add(
            Course(
                id = "p3",
                categoryId = "pc_skills",
                title = "AI & Machine Learning",
                description = "Code modern neural networks, predictive models, and prompt automation tools using Python.",
                duration = "10 Weeks",
                lessonsCount = 40,
                rating = 4.8,
                instructor = "Sneha Iyer",
                iconName = "terminal"
            )
        )
        localCourses.add(
            Course(
                id = "p4",
                categoryId = "pc_skills",
                title = "Graphics Design",
                description = "Learn masterclass workflows in Adobe Photoshop, Illustrator, and Figma.",
                duration = "9 Weeks",
                lessonsCount = 30,
                rating = 4.7,
                instructor = "Pranav Deshmukh",
                iconName = "palette"
            )
        )
        localCourses.add(
            Course(
                id = "p5",
                categoryId = "pc_skills",
                title = "DSA (Data Structures & Algorithms)",
                description = "Crack interviews with comprehensive mastery of Arrays, Graphs, Dynamic Programming, and sorting.",
                duration = "14 Weeks",
                lessonsCount = 60,
                rating = 4.9,
                instructor = "Sandip Banerjee",
                iconName = "reorder"
            )
        )
    }

    // AUTH ACTIONS
    fun isUserLoggedIn(): Boolean {
        return if (isFirebaseAvailable && auth?.currentUser != null) {
            true
        } else {
            localCurrentUser != null
        }
    }

    fun getCurrentUserEmail(): String {
        return if (isFirebaseAvailable && auth?.currentUser != null) {
            auth?.currentUser?.email ?: ""
        } else {
            localCurrentUser?.email ?: ""
        }
    }

    fun getCurrentUserId(): String {
        return if (isFirebaseAvailable && auth?.currentUser != null) {
            auth?.currentUser?.uid ?: ""
        } else {
            localCurrentUser?.id ?: ""
        }
    }

    fun signInWithGoogle(email: String, fullName: String, onResult: (Boolean, String?) -> Unit) {
        if (isFirebaseAvailable && auth != null) {
            // Simulated login triggered inside standard Firebase project setup.
            // Since we can't perform true Oauth popups easily on a headless Android system, we trigger
            // profile lookup or create the Firestore record under the authenticated user.
            Log.d(TAG, "Firebase signing in simulated: $email")
            
            // Query firestore to find user profile
            val uid = "google_user_${email.replace(".", "_")}"
            firestore?.collection("users")?.document(uid)?.get()
                ?.addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        val user = doc.toObject(User::class.java)
                        if (user != null) {
                            localCurrentUser = user
                            onResult(true, null)
                        } else {
                            // Create user
                            createNewUserInFirestore(uid, email, fullName, onResult)
                        }
                    } else {
                        createNewUserInFirestore(uid, email, fullName, onResult)
                    }
                }
                ?.addOnFailureListener { err ->
                    // Network issues or permission issues: fallback to local simulated profile
                    Log.w(TAG, "Firestore lookup failed, falling back to local simulation.", err)
                    handleLocalLogin(uid, email, fullName, onResult)
                }
        } else {
            val uid = "google_user_${email.replace(".", "_")}"
            handleLocalLogin(uid, email, fullName, onResult)
        }
    }

    private fun createNewUserInFirestore(uid: String, email: String, fullName: String, onResult: (Boolean, String?) -> Unit) {
        val newUser = User(
            id = uid,
            fullName = fullName,
            email = email,
            authProvider = "google",
            isProfileCompleted = false
        )
        firestore?.collection("users")?.document(uid)?.set(newUser)
            ?.addOnSuccessListener {
                localCurrentUser = newUser
                onResult(true, null)
            }
            ?.addOnFailureListener { err ->
                Log.w(TAG, "Firestore write failed", err)
                localCurrentUser = newUser
                onResult(true, "Successfully logged in offline due to Firebase sync parameters.")
            }
    }

    private fun handleLocalLogin(uid: String, email: String, fullName: String, onResult: (Boolean, String?) -> Unit) {
        val existing = localUsers[uid]
        if (existing != null) {
            localCurrentUser = existing
        } else {
            val newUser = User(
                id = uid,
                fullName = fullName,
                email = email,
                authProvider = "google",
                isProfileCompleted = false
            )
            localUsers[uid] = newUser
            localCurrentUser = newUser
        }
        onResult(true, null)
    }

    fun logout() {
        if (isFirebaseAvailable && auth != null) {
            auth?.signOut()
        }
        localCurrentUser = null
    }

    // PROFILE ACTIONS
    fun saveUserProfile(user: User, onResult: (Boolean, String?) -> Unit) {
        user.isProfileCompleted = true
        user.lastLogin = System.currentTimeMillis()

        if (isFirebaseAvailable && firestore != null) {
            val uid = getCurrentUserId().ifEmpty { "google_user_${user.email.replace(".", "_")}" }
            firestore?.collection("users")?.document(uid)?.set(user)
                ?.addOnSuccessListener {
                    localCurrentUser = user
                    localUsers[uid] = user
                    Log.d(TAG, "Profile saved to Firestore successfully.")
                    onResult(true, null)
                }
                ?.addOnFailureListener { err ->
                    Log.e(TAG, "Failed to save profile to Firestore", err)
                    // Save locally anyway
                    localCurrentUser = user
                    localUsers[uid] = user
                    onResult(true, "Saved locally. Firebase Write Error: ${err.localizedMessage}")
                }
        } else {
            val uid = getCurrentUserId().ifEmpty { "google_user_${user.email.replace(".", "_")}" }
            user.id = uid
            localCurrentUser = user
            localUsers[uid] = user
            onResult(true, null)
        }
    }

    fun getUserProfile(userId: String, onResult: (User?) -> Unit) {
        if (isFirebaseAvailable && firestore != null) {
            firestore?.collection("users")?.document(userId)?.get()
                ?.addOnSuccessListener { doc ->
                    if (doc.exists()) {
                        val user = doc.toObject(User::class.java)
                        onResult(user)
                    } else {
                        onResult(localUsers[userId] ?: localCurrentUser)
                    }
                }
                ?.addOnFailureListener {
                    onResult(localUsers[userId] ?: localCurrentUser)
                }
        } else {
            onResult(localUsers[userId] ?: localCurrentUser)
        }
    }

    // COURSE DATA ACTIONS
    fun getCourseCategories(onResult: (List<CourseCategory>) -> Unit) {
        if (isFirebaseAvailable && firestore != null) {
            firestore?.collection("courseCategories")?.get()
                ?.addOnSuccessListener { snapshot ->
                    val categories = mutableListOf<CourseCategory>()
                    for (doc in snapshot.documents) {
                        val cat = doc.toObject(CourseCategory::class.java)
                        if (cat != null) {
                            categories.add(cat)
                        }
                    }
                    if (categories.isEmpty()) {
                        // Populate remote firestore if empty so they match!
                        populateFirestoreData()
                        onResult(localCategories)
                    } else {
                        onResult(categories)
                    }
                }
                ?.addOnFailureListener {
                    Log.w(TAG, "Failed to query categories from Firestore, using offline engine.", it)
                    onResult(localCategories)
                }
        } else {
            onResult(localCategories)
        }
    }

    fun getCourses(onResult: (List<Course>) -> Unit) {
        if (isFirebaseAvailable && firestore != null) {
            firestore?.collection("courses")?.get()
                ?.addOnSuccessListener { snapshot ->
                    val coursesList = mutableListOf<Course>()
                    for (doc in snapshot.documents) {
                        val c = doc.toObject(Course::class.java)
                        if (c != null) {
                            coursesList.add(c)
                        }
                    }
                    if (coursesList.isEmpty()) {
                        onResult(localCourses)
                    } else {
                        onResult(coursesList)
                    }
                }
                ?.addOnFailureListener {
                    onResult(localCourses)
                }
        } else {
            onResult(localCourses)
        }
    }

    private fun populateFirestoreData() {
        if (!isFirebaseAvailable || firestore == null) return
        Log.d(TAG, "Populating base Firestore catalog data...")
        
        // Write categories
        for (cat in localCategories) {
            firestore?.collection("courseCategories")?.document(cat.id)?.set(cat)
        }
        // Write courses
        for (course in localCourses) {
            firestore?.collection("courses")?.document(course.id)?.set(course)
        }
    }
}
