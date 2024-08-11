package com.example.cfh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cfh.domain.models.NavigationItem
import com.example.cfh.presentaion.details.DetailScreen
import com.example.cfh.presentaion.home.HomeScreen
import com.example.cfh.ui.theme.CFHTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {

                val navController = rememberNavController()
                MyApp {

                    NavHost(
                        navController = navController,
                        startDestination = NavigationItem.Home.route
                    )
                    {

                        composable(NavigationItem.Home.route) {

                            HomeScreen(navController)
                        }
                        composable("detail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")
                            requireNotNull(movieId) { "Movie parameter wasn't found. Please make sure it's set!" }
                            DetailScreen(navController, movieId)
                        }


                    }

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
    CFHTheme {
        Greeting("Android")
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    content()

}