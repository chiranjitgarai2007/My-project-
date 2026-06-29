package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.MainViewModel

// Avatar presets representing creative professional roles
data class AvatarPreset(val id: String, val emoji: String, val name: String, val color: Color)

@Composable
fun ProfileSetupScreen(
    viewModel: MainViewModel,
    onSetupComplete: () -> Unit
) {
    val currentUser by viewModel.currentUser.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var fullName by remember { mutableStateOf(currentUser?.fullName ?: "") }
    var phone by remember { mutableStateOf(currentUser?.phone ?: "") }
    var address by remember { mutableStateOf(currentUser?.address ?: "") }
    
    val avatarPresets = listOf(
        AvatarPreset("editor", "🎬", "Video Editor", Color(0xFFFF5252)),
        AvatarPreset("ai", "🤖", "AI Creator", Color(0xFF00E676)),
        AvatarPreset("code", "💻", "Developer", Color(0xFF2979FF)),
        AvatarPreset("design", "🎨", "Designer", Color(0xFFD500F9)),
        AvatarPreset("yt", "📺", "YouTuber", Color(0xFFFFD600)),
        AvatarPreset("social", "📱", "Social Guru", Color(0xFF00E5FF))
    )
    
    var selectedAvatar by remember { mutableStateOf(avatarPresets[0]) }

    val isDark = true
    val backgroundColor = Color(0xFF0F0C20)
    val cardBackground = Color(0xFF1B1635)
    val textPrimary = Color.White
    val textSecondary = Color.White.copy(alpha = 0.65f)
    val accentColor = Color(0xFF00F2FE)

    LaunchedEffect(currentUser) {
        currentUser?.let {
            if (fullName.isEmpty()) fullName = it.fullName
            if (phone.isEmpty()) phone = it.phone
            if (address.isEmpty()) address = it.address
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(backgroundColor, Color(0xFF161033))
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 480.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Complete Your Profile",
                color = textPrimary,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Help us personalize your digital skills curriculum",
                color = textSecondary,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp, bottom = 24.dp)
            )

            // Form Card
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(12.dp, RoundedCornerShape(20.dp))
                    .testTag("profile_setup_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = cardBackground)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    
                    // Display prefilled email
                    Text(
                        text = "ACCOUNT EMAIL",
                        color = textSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(10.dp))
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Email, "Email", tint = accentColor, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = currentUser?.email ?: "chiranjit973214@gmail.com",
                            color = textPrimary.copy(alpha = 0.9f),
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Fields
                    OutlinedTextField(
                        value = fullName,
                        onValueChange = { fullName = it },
                        label = { Text("Full Name", color = textSecondary) },
                        leadingIcon = { Icon(Icons.Default.Person, null, tint = accentColor) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = textSecondary.copy(alpha = 0.3f),
                            focusedLabelColor = accentColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("fullName_field"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Contact Number", color = textSecondary) },
                        leadingIcon = { Icon(Icons.Default.Phone, null, tint = accentColor) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = textSecondary.copy(alpha = 0.3f),
                            focusedLabelColor = accentColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("contact_field"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = address,
                        onValueChange = { address = it },
                        label = { Text("Physical Address", color = textSecondary) },
                        leadingIcon = { Icon(Icons.Default.Home, null, tint = accentColor) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary,
                            focusedBorderColor = accentColor,
                            unfocusedBorderColor = textSecondary.copy(alpha = 0.3f),
                            focusedLabelColor = accentColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .testTag("address_field"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Avatar Selection Profile image
                    Text(
                        text = "CHOOSE LEARNING PROFILE AVATAR",
                        color = textSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Horizontal circular selection or 2x3 Grid (Using a Box Flow or Row column to avoid nesting scroll issues)
                    Column(modifier = Modifier.fillMaxWidth()) {
                        val chunkedPresets = avatarPresets.chunked(3)
                        for (row in chunkedPresets) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                for (preset in row) {
                                    val isSelected = selectedAvatar.id == preset.id
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .padding(4.dp)
                                            .background(
                                                if (isSelected) preset.color.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.03f),
                                                RoundedCornerShape(12.dp)
                                            )
                                            .border(
                                                width = if (isSelected) 2.dp else 1.dp,
                                                color = if (isSelected) preset.color else textSecondary.copy(alpha = 0.1f),
                                                shape = RoundedCornerShape(12.dp)
                                            )
                                            .clickable { selectedAvatar = preset }
                                            .padding(10.dp)
                                            .testTag("avatar_${preset.id}"),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(preset.emoji, fontSize = 24.sp)
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = preset.name,
                                                color = textPrimary,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(28.dp))

                    // Submit Button
                    Button(
                        onClick = {
                            if (fullName.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty()) {
                                viewModel.completeProfile(
                                    fullName = fullName,
                                    phone = phone,
                                    address = address,
                                    profileImage = selectedAvatar.emoji, // Store emoji as profile icon
                                    onSuccess = onSetupComplete
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("complete_profile_submit"),
                        colors = ButtonDefaults.buttonColors(containerColor = accentColor),
                        shape = RoundedCornerShape(12.dp),
                        enabled = fullName.isNotEmpty() && phone.isNotEmpty() && address.isNotEmpty() && !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                        } else {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Launch My Journey",
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(Icons.Default.KeyboardArrowRight, null, tint = Color.Black)
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
