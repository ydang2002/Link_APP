package com.example.linkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.linkapp.data.LinkDatabase
import com.example.linkapp.data.LinkRepository
import com.example.linkapp.ui.theme.LinkAppTheme
import com.example.linkapp.viemodel.LinkViewModelNew
import com.example.linkapp.viemodel.LinkViewModelFactory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import android.widget.Toast
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Khởi tạo database, repository và ViewModel
        val database = LinkDatabase.getDatabase(applicationContext)
        val repository = LinkRepository(database.linkDao())
        val viewModelFactory = LinkViewModelFactory(repository)
        val linkViewModel = ViewModelProvider(this, viewModelFactory)[LinkViewModelNew::class.java]

        setContent {
            LinkAppTheme {
                DroneControlScreen()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    MainScreen(
//                        viewModel = linkViewModel,
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
            }
        }
    }
}

@Composable
fun DroneControlScreen() {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding() // Tránh bị che bởi system bars
    ) {
        // Camera video background
//        CameraPreview()

        CameraPreviewAndMiniMap()

        // Top status bar
        TopBar(context)

        // Side control menu
        SideMenu(
            Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp), context
        )

        // Mini map
//        MiniMap(Modifier.align(Alignment.BottomStart).padding(12.dp))

        // Flight info
//        FlightData(Modifier.align(Alignment.BottomCenter).padding(bottom = 12.dp))

        // Right camera control + shutter
        RightPanel(
            Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp), context
        )
    }
}

@Composable
fun CameraPreviewAndMiniMap(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        CameraPreview()
        Row(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            MiniMap()
            Spacer(modifier = Modifier.width(12.dp))
            FlightData()
        }
    }
}

@Composable
fun CameraPreview() {
    // Dùng ảnh giả thay cho video (thay bằng R.drawable.sample_image thực tế bạn có)
    Image(
        painter = painterResource(id = R.drawable.drone_view_background), // giả sử tên là drone_view_background.png
        contentDescription = "Drone Camera View",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun TopBar(context: Context) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(Color(0x66000000))
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Settings, contentDescription = null, tint = Color.White)
            Spacer(modifier = Modifier.width(8.dp))
            StatusChip("Ready", Color(0xFF00C853), onClick = {
                showToast(context, "Status: Ready")
            })
            Spacer(modifier = Modifier.width(4.dp))
            StatusChip("Hold", Color.Gray, onClick = {
                showToast(context, "Status: Hold")
            })
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    showToast(context, "clicked 1")
                })
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    showToast(context, "clicked 2")
                })
            Icon(
                Icons.Default.Settings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.clickable {
                    showToast(context, "clicked 3")
                })
        }
    }
}

@Composable
fun StatusChip(text: String, color: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(color, RoundedCornerShape(6.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, fontSize = 12.sp, color = Color.White)
    }
}

@Composable
fun SideMenu(modifier: Modifier = Modifier, context: Context) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp), // Tăng khoảng trắng lên 12.dp để rõ ràng hơn
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconBox(
            icon = Icons.Default.Settings,
            message = "Settings",
            context = context,
            shape = RoundedCornerShape(
                topStart = 10.dp,
                topEnd = 10.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ) // Bo góc trên, tăng lên 10.dp
        )
        IconBox(
            icon = Icons.Default.KeyboardArrowUp,
            message = "Move Up",
            context = context,
            shape = RoundedCornerShape(0.dp) // Không bo góc
        )
        IconBox(
            icon = Icons.Default.KeyboardArrowDown,
            message = "Move Down",
            context = context,
            shape = RoundedCornerShape(
                bottomStart = 10.dp,
                bottomEnd = 10.dp,
                topStart = 0.dp,
                topEnd = 0.dp
            ) // Bo góc dưới, tăng lên 10.dp
        )
    }
}

@Composable
fun IconBox(icon: ImageVector, message: String, context: Context, shape: RoundedCornerShape) {
    Box(
        modifier = Modifier
            .size(width = 40.dp, height = 40.dp) // Tăng kích thước lên 40.dp để khớp với hình
            .clip(shape) // Áp dụng bo góc
            .background(Color(0xAA333333)) // Màu nền xám
    ) {
        IconButton(
            onClick = { showToast(context, "Click $message") },
            modifier = Modifier
                .fillMaxSize() // Đảm bảo IconButton lấp đầy Box
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(24.dp) // Đảm bảo icon không quá lớn
            )
        }
    }
}

@Composable
fun MiniMap(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(width = 180.dp, height = 140.dp)
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(id = R.drawable.mini_map_placeholder),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun FlightData(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Column {
                Text("↑ 24.3ft", color = Color.White, fontSize = 14.sp)
                Text("→ 22.1ft", color = Color.White, fontSize = 14.sp)
            }
            Column {
                Text("0.0 mph", color = Color.White, fontSize = 14.sp)
                Text("00:00:00", color = Color.White, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun RightPanel(modifier: Modifier = Modifier, context: Context) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top control panel with camera icons + switch
        Box(
            modifier = Modifier
                .width(64.dp)
                .height(72.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xAA333333)) // mờ + bo góc
                .padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { showToast(context, "Click Camera") },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Call,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IconButton(
                        onClick = { showToast(context, "Click Switch Camera") },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Switch(
                    checked = false,
                    onCheckedChange = { showToast(context, "Click Switch") },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        uncheckedThumbColor = Color.LightGray,
                        uncheckedTrackColor = Color.DarkGray,
                        checkedTrackColor = Color.Gray
                    ),
                    modifier = Modifier.scale(0.8f)
                )

            }
        }

        // Large shutter button
        Button(
            onClick = { showToast(context, "Click Hold") },
            modifier = Modifier.size(64.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {}
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}