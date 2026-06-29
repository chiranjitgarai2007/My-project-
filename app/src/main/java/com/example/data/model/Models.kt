package com.example.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName

data class User(
    @DocumentId
    var id: String = "",
    @get:PropertyName("fullName") @set:PropertyName("fullName")
    var fullName: String = "",
    @get:PropertyName("email") @set:PropertyName("email")
    var email: String = "",
    @get:PropertyName("phone") @set:PropertyName("phone")
    var phone: String = "",
    @get:PropertyName("address") @set:PropertyName("address")
    var address: String = "",
    @get:PropertyName("profileImage") @set:PropertyName("profileImage")
    var profileImage: String = "",
    @get:PropertyName("role") @set:PropertyName("role")
    var role: String = "student", // "student", "admin"
    @get:PropertyName("createdAt") @set:PropertyName("createdAt")
    var createdAt: Long = System.currentTimeMillis(),
    @get:PropertyName("lastLogin") @set:PropertyName("lastLogin")
    var lastLogin: Long = System.currentTimeMillis(),
    @get:PropertyName("authProvider") @set:PropertyName("authProvider")
    var authProvider: String = "google",
    @get:PropertyName("isProfileCompleted") @set:PropertyName("isProfileCompleted")
    var isProfileCompleted: Boolean = false
)

data class CourseCategory(
    @DocumentId
    val id: String = "",
    val name: String = "",
    val iconName: String = "", // e.g., "smartphone", "desktop"
    val description: String = ""
)

data class Course(
    @DocumentId
    val id: String = "",
    val categoryId: String = "", // "mobile_skills", "pc_skills"
    val title: String = "",
    val description: String = "",
    val duration: String = "",
    val lessonsCount: Int = 0,
    val rating: Double = 4.8,
    val instructor: String = "Expert Educator",
    val isPremium: Boolean = true,
    val iconName: String = "" // Material Icon identifier
)
