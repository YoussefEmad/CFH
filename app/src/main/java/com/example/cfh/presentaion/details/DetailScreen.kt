package com.example.cfh.presentaion.details

import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cfh.presentaion.componant.Loader
import com.example.cfh.presentaion.componant.MovieDetails
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(
    navController: NavController,
    movieId: String,
    viewModel: DetailViewModel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            viewModel.handleAction(DeatalisContract.Action.GetMovie(movieId.toInt()))
        }
    }
    val uiState by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        content = {
            when {
                uiState.isLoading -> {
                    Loader()
                }

                uiState.movie != null -> {
                    Log.d("TAG", "DetailScreen: " + uiState.movie)
                    MovieDetails(uiState.movie!!.getSuccessData())
                }

            }
        })
}