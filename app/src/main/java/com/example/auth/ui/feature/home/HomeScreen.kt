package com.example.auth.ui.feature.home

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(onLogout: () -> Unit) {

    val journeyBackground = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF0E6FF),
            Color(0xFFF8F9FE),
            Color(0xFFE6F0FF)
        )
    )


    val mainPurple = Color(0xFF9D50FF)

    val user = FirebaseAuth.getInstance().currentUser
    val displayName = user?.displayName ?: "Explorer"
    val email = user?.email ?: "Not available"

    val infiniteTransition = rememberInfiniteTransition(label = "StarRotation")
    val starAngle by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "RotateStar"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(journeyBackground)
            .padding(24.dp)
            .statusBarsPadding()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.titleMedium,
                color = mainPurple,
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = mainPurple.copy(alpha = 0.5f),
                modifier = Modifier
                    .size(24.dp)
                    .scale(starAngle.coerceIn(0f, 1f)) // A subtle subtle dynamic look
            )
        }

        Spacer(modifier = Modifier.height(48.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(4.dp, mainPurple.copy(alpha = 0.3f), CircleShape)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {

                Surface(
                    modifier = Modifier.fillMaxSize().scale(0.9f),
                    color = mainPurple.copy(alpha = 0.1f),
                    shape = CircleShape
                ) {}

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    tint = mainPurple,
                    modifier = Modifier.size(64.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = displayName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1C1E)
            )
            Text(
                text = "Welcome to your destination.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "ACCOUNT DETAILS",
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                InfoRow(icon = Icons.Default.Person, title = "Name", value = displayName)
                Divider(color = Color(0xFFE0E0E0), modifier = Modifier.padding(vertical = 16.dp))
                InfoRow(icon = Icons.Default.Email, title = "Email", value = email)
            }
        }

        Spacer(modifier = Modifier.height(48.dp))


        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = mainPurple)
        ) {
            Text("Logout", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color(0xFF1A1C1E))
        }
    }
}