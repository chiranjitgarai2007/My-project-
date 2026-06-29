package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Course
import com.example.data.model.CourseCategory
import com.example.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToComingSoon: (String) -> Unit,
    onLogoutSuccess: () -> Unit
) {
    var activeTab by remember { mutableIntStateOf(0) } // 0 = Home, 1 = Profile (Purchase & Community redirect)
    var selectedCategoryId by remember { mutableStateOf("all") } // "all", "mobile_skills", "pc_skills"

    val currentUser by viewModel.currentUser.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val courses by viewModel.courses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Premium styling parameters
    val backgroundColor = Color(0xFF0F0C20)
    val cardBackground = Color(0xFF1B1635)
    val textPrimary = Color.White
    val textSecondary = Color.White.copy(alpha = 0.65f)
    val accentColor = Color(0xFF00F2FE)
    val accentSecondary = Color(0xFF4FACFE)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = backgroundColor,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "My Skills, My Dreams",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = textPrimary
                        )
                        Text(
                            text = "Brand Tagline: Invest & Realize",
                            fontSize = 11.sp,
                            color = accentColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateToComingSoon("Side Menu Dashboard") }) {
                        Icon(Icons.Default.Menu, "Menu", tint = textPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { onNavigateToComingSoon("Notifications Center") }) {
                        Icon(Icons.Default.Notifications, "Notifications", tint = textPrimary)
                    }
                    IconButton(onClick = { onNavigateToComingSoon("App Settings") }) {
                        Icon(Icons.Default.Settings, "Settings", tint = textPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = textPrimary,
                    navigationIconContentColor = textPrimary,
                    actionIconContentColor = textPrimary
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = cardBackground,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    icon = { Icon(Icons.Default.Home, null) },
                    label = { Text("Home", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = accentColor,
                        indicatorColor = accentColor,
                        unselectedIconColor = textSecondary,
                        unselectedTextColor = textSecondary
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToComingSoon("Your Purchase Batch") },
                    icon = { Icon(Icons.Default.Book, null) },
                    label = { Text("My Batches", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = textSecondary,
                        unselectedTextColor = textSecondary
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigateToComingSoon("Community Forum") },
                    icon = { Icon(Icons.Default.Group, null) },
                    label = { Text("Community", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = textSecondary,
                        unselectedTextColor = textSecondary
                    )
                )
                NavigationBarItem(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile", fontSize = 11.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = Color.Black,
                        selectedTextColor = accentColor,
                        indicatorColor = accentColor,
                        unselectedIconColor = textSecondary,
                        unselectedTextColor = textSecondary
                    )
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (activeTab == 0) {
                // HOME VIEW CONTENT
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))
                        // Hero Spotlight Banner
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(accentSecondary, Color(0xFF1B1635))
                                    )
                                )
                                .padding(18.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                Color.White.copy(alpha = 0.15f),
                                                RoundedCornerShape(50)
                                            )
                                            .padding(horizontal = 12.dp, vertical = 4.dp)
                                    ) {
                                        Text(
                                            "LAUNCH PROMO: V1 SETUP",
                                            color = accentColor,
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Master Digital Skills & Make Your Dreams Real",
                                        color = textPrimary,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        lineHeight = 22.sp
                                    )
                                }
                                Text(
                                    text = "Learn mobile & PC masterclasses step-by-step.",
                                    color = textSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }

                    // Interactive Shortcut Row: Live Classes, Certificates, Payments, Chats, Admin
                    item {
                        Column {
                            Text(
                                "EXPLORE PROGRAMS",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    ShortcutCard(
                                        title = "Live Classes",
                                        icon = Icons.Default.Videocam,
                                        color = Color(0xFFFF5252),
                                        onClick = { onNavigateToComingSoon("Live Classes") }
                                    )
                                }
                                item {
                                    ShortcutCard(
                                        title = "Certificates",
                                        icon = Icons.Default.WorkspacePremium,
                                        color = Color(0xFFFFD600),
                                        onClick = { onNavigateToComingSoon("Student Certificates") }
                                    )
                                }
                                item {
                                    ShortcutCard(
                                        title = "Payment System",
                                        icon = Icons.Default.Payment,
                                        color = Color(0xFF00E676),
                                        onClick = { onNavigateToComingSoon("Payment System") }
                                    )
                                }
                                item {
                                    ShortcutCard(
                                        title = "Chat Support",
                                        icon = Icons.Default.Forum,
                                        color = Color(0xFF2979FF),
                                        onClick = { onNavigateToComingSoon("Chat Support System") }
                                    )
                                }
                                item {
                                    ShortcutCard(
                                        title = "Admin Portal",
                                        icon = Icons.Default.AdminPanelSettings,
                                        color = Color(0xFFD500F9),
                                        onClick = { onNavigateToComingSoon("Admin Dashboard") }
                                    )
                                }
                            }
                        }
                    }

                    // Categories Filtering Tab Row (Dynamically populated from Firestore Categories!)
                    item {
                        Column {
                            Text(
                                "SKILLS CURRICULUM",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = accentColor,
                                letterSpacing = 1.sp,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                item {
                                    FilterChip(
                                        selected = selectedCategoryId == "all",
                                        onClick = { selectedCategoryId = "all" },
                                        label = { Text("All Courses") },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = accentColor,
                                            selectedLabelColor = Color.Black,
                                            containerColor = cardBackground,
                                            labelColor = textSecondary
                                        ),
                                        border = null
                                    )
                                }
                                items(categories) { cat ->
                                    FilterChip(
                                        selected = selectedCategoryId == cat.id,
                                        onClick = { selectedCategoryId = cat.id },
                                        label = { Text(cat.name) },
                                        colors = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = accentColor,
                                            selectedLabelColor = Color.Black,
                                            containerColor = cardBackground,
                                            labelColor = textSecondary
                                        ),
                                        border = null,
                                        modifier = Modifier.testTag("category_chip_${cat.id}")
                                    )
                                }
                            }
                        }
                    }

                    // Courses dynamic container loader
                    val filteredCourses = if (selectedCategoryId == "all") {
                        courses
                    } else {
                        courses.filter { it.categoryId == selectedCategoryId }
                    }

                    if (isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = accentColor)
                            }
                        }
                    } else if (filteredCourses.isEmpty()) {
                        item {
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = cardBackground)
                            ) {
                                Column(
                                    modifier = Modifier.padding(24.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(Icons.Default.Info, null, tint = accentColor, modifier = Modifier.size(32.dp))
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text("No courses loaded from Firestore yet.", color = textPrimary, fontWeight = FontWeight.Bold)
                                    Text("Pull dynamic courses from repository config.", color = textSecondary, fontSize = 12.sp, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    } else {
                        items(filteredCourses) { course ->
                            CourseListItem(
                                course = course,
                                cardBg = cardBackground,
                                textPrimary = textPrimary,
                                textSecondary = textSecondary,
                                accentColor = accentColor,
                                onEnrollClick = { onNavigateToComingSoon("Enroll in ${course.title}") }
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            } else {
                // PROFILE VIEW TAB CONTENT
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Large User Icon badge matching chosen avatar preset
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Brush.linearGradient(listOf(accentColor, accentSecondary)))
                    ) {
                        Text(
                            text = currentUser?.profileImage?.ifEmpty { "🎓" } ?: "🎓",
                            fontSize = 48.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentUser?.fullName ?: "Chiranjit",
                        color = textPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Access Role: ${currentUser?.role?.replaceFirstChar { it.uppercase() } ?: "Student"}",
                        color = accentColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Detail Form Fields
                    ElevatedCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(4.dp, RoundedCornerShape(16.dp)),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = cardBackground)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            ProfileDetailRow("Email", currentUser?.email ?: "chiranjit973214@gmail.com", Icons.Default.Email, textPrimary, textSecondary)
                            HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))
                            ProfileDetailRow("Phone", currentUser?.phone ?: "Not Provided", Icons.Default.Phone, textPrimary, textSecondary)
                            HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))
                            ProfileDetailRow("Address", currentUser?.address ?: "Not Provided", Icons.Default.LocationOn, textPrimary, textSecondary)
                            HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))
                            
                            val creationDate = currentUser?.createdAt ?: System.currentTimeMillis()
                            val formattedDate = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(creationDate))
                            ProfileDetailRow("Registered", formattedDate, Icons.Default.CalendarToday, textPrimary, textSecondary)
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Session Actions (Logout)
                    Button(
                        onClick = {
                            viewModel.logoutUser {
                                onLogoutSuccess()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("logout_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5252)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ExitToApp, null, tint = Color.White)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sign Out of Session", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShortcutCard(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1B1635))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(color.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, title, tint = color, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CourseListItem(
    course: Course,
    cardBg: Color,
    textPrimary: Color,
    textSecondary: Color,
    accentColor: Color,
    onEnrollClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, RoundedCornerShape(16.dp))
            .testTag("course_item_${course.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = cardBg)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Category Tag
                Box(
                    modifier = Modifier
                        .background(accentColor.copy(alpha = 0.1f), RoundedCornerShape(50))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = if (course.categoryId == "mobile_skills") "MOBILE SKILL" else "LAPTOP / PC SKILL",
                        color = accentColor,
                        fontSize = 8.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }

                // Rating badge
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, "Rating", tint = Color(0xFFFFD600), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(course.rating.toString(), color = textPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Course Title
            Text(
                text = course.title,
                color = textPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Course Description
            Text(
                text = course.description,
                color = textSecondary,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Meta Details
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PlayCircle, null, tint = textSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${course.lessonsCount} lessons", color = textSecondary, fontSize = 11.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(Icons.Default.AccessTime, null, tint = textSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(course.duration, color = textSecondary, fontSize = 11.sp)
                }

                Text(
                    text = "Instructor: ${course.instructor}",
                    color = accentColor.copy(alpha = 0.8f),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            HorizontalDivider(color = Color.White.copy(alpha = 0.05f), modifier = Modifier.padding(vertical = 12.dp))

            // Bottom CTA row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("TUITION PRICE", color = textSecondary, fontSize = 9.sp)
                    Text("FREE (V1 Launch)", color = textPrimary, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold)
                }

                Button(
                    onClick = { onEnrollClick() },
                    colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Get Details", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, null, tint = Color.Black, modifier = Modifier.size(14.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(
    label: String,
    value: String,
    icon: ImageVector,
    textPrimary: Color,
    textSecondary: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.05f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = Color(0xFF00F2FE), modifier = Modifier.size(18.dp))
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(label.uppercase(), color = textSecondary, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = textPrimary, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}
