package com.example.cfh.presentaion.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cfh.ui.theme.CFHTheme
import coil.compose.rememberImagePainter
import com.example.cfh.data.models.LocalMovieModel
import com.example.cfh.domain.models.MovieTypes
import com.example.cfh.presentaion.componant.LoadingShimmerEffect
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            if (viewModel.uiState.value.nowPlayingMovies.equals(null))
                viewModel.handleAction(HomeContractor.Action.GetMovies(Type = MovieTypes.PLAYING_NOW.value))
            if (viewModel.uiState.value.upcomingMovies.equals(null))
                viewModel.handleAction(HomeContractor.Action.GetMovies(Type = MovieTypes.UPCOMING.value))
            if (viewModel.uiState.value.popularMovies.equals(null))
                viewModel.handleAction(HomeContractor.Action.GetMovies(Type = MovieTypes.POPULAR.value))
        }
    }


    val uiState by viewModel.uiState.collectAsState()

    LazyColumn (  modifier = Modifier
        .fillMaxSize()
        .padding(8.dp)
    ){
        item {
            SectionTitle("Now Playing")
            uiState.nowPlayingMovies.let {
                MoviesList(it.collectAsLazyPagingItems(),navController)
            }
        }
        item {
            SectionTitle("Popular")
            uiState.popularMovies.let {
                MoviesList(it.collectAsLazyPagingItems(), navController)
            }
        }
        item {
            SectionTitle("Upcoming")
            uiState.upcomingMovies.let {
                MoviesList(it.collectAsLazyPagingItems(), navController)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
        modifier = Modifier.padding(8.dp)
    )
}


@Composable
fun MoviesList(lazyPagingItems: LazyPagingItems<LocalMovieModel>?, navController: NavController) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        lazyPagingItems?.let {
            if (lazyPagingItems.loadState.refresh is LoadState.Loading) {
                items(10) {
                    LoadingShimmerEffect()
                }
            }
            items(
                count = it.itemCount,
                key = { index -> lazyPagingItems[index]?.id ?: index },

            ) { index ->
                val item = lazyPagingItems[index]
                item?.let {
                    MovieItem(movie = it, navController = navController)
                }
            }
        }
    }
}


@Composable
fun MovieItem(movie: LocalMovieModel, navController: NavController) {
    Column(modifier = Modifier.padding(8.dp)) {
        Image(
            painter = rememberImagePainter(data = "https://image.tmdb.org/t/p/w500${movie.poster_path}"),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(100.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable {
                    navController.navigate("detail/${movie.id}")
                }
        )
        Text(
            text = movie.title ?: "",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier
                .padding(top = 8.dp)
                .width(100.dp),
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = movie.release_date ?: "",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable

fun CardItemPreview() {
    CFHTheme {

    }
}

