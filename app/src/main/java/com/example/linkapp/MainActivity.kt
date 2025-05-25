package com.example.linkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.example.linkapp.data.LinkDatabase
import com.example.linkapp.data.LinkRepository
import com.example.linkapp.ui.MainScreen
import com.example.linkapp.ui.theme.LinkAppTheme
import com.example.linkapp.viemodel.LinkViewModel
import com.example.linkapp.viemodel.LinkViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Khởi tạo database, repository và ViewModel
        val database = LinkDatabase.getDatabase(applicationContext)
        val repository = LinkRepository(database.linkDao())
        val viewModelFactory = LinkViewModelFactory(repository)
        val linkViewModel = ViewModelProvider(this, viewModelFactory)[LinkViewModel::class.java]

        setContent {
            LinkAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        viewModel = linkViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LinkAppTheme {
        Greeting("Android")
    }
}