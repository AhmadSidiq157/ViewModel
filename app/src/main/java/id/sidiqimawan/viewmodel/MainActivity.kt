package id.sidiqimawan.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.sidiqimawan.viewmodel.ui.theme.ViewModelTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InfiniteScrollListScreen()
                }
            }
        }
    }
}

@Composable
fun InfiniteScrollListScreen(viewModel: ListViewModel = viewModel()) {
    val items by viewModel.items.collectAsState()
    val itemCount = items.size

    InfiniteScrollList(
        itemCount = itemCount,
        loadMoreItems = { viewModel.loadMoreItems() }
    ) { index ->
        Text(
            text = items[index],
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun InfiniteScrollList(
    itemCount: Int,
    loadMoreItems: () -> Unit,
    content: @Composable (Int) -> Unit
) {
    val listState = rememberLazyListState()

    // Trigger load more when user reaches bottom
    LaunchedEffect(listState.firstVisibleItemIndex) {
        val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        if (lastVisibleItem == itemCount - 1) {
            loadMoreItems()
        }
    }

    LazyColumn(state = listState) {
        items(itemCount) { index ->
            content(index)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ViewModelTheme {
        InfiniteScrollListScreen()
    }
}
