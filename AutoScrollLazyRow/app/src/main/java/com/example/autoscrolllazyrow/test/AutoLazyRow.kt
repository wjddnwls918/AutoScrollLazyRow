package com.example.autoscrolllazyrow.test

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autoscrolllazyrow.CommonViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun AutoScrollLazyRow(modifier: Modifier = Modifier) {
    LazyRow(modifier = modifier) {

    }
}

private const val SCROLL_DX = 24f
private const val REQUIRED_CARD_COUNT = 8

private class AutoScrollItem<T>(
    val id: String = UUID.randomUUID().toString(),
    val data: T
)

@Composable
fun <T : Any> AutoScrollingLazyRow(
    list: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (item: T) -> Unit,
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    var items by remember { mutableStateOf(list.mapAutoScrollItem()) }

    LazyRow(
        state = lazyListState,
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(
            items, key = { _, item -> item.id }
        ) { index, item ->
            itemContent(item = item.data)

            if (index == items.lastIndex) {
                val currentList = items
                val firstVisibleItemIndex = lazyListState.firstVisibleItemIndex
                val secondPart = currentList.subList(0, firstVisibleItemIndex)
                val firstPart =
                    currentList.subList(firstVisibleItemIndex, currentList.size)

                LaunchedEffect(key1 = Unit) {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(
                            0,
                            maxOf(0, lazyListState.firstVisibleItemScrollOffset - SCROLL_DX.toInt())
                        )
                    }
                }

                items = (firstPart + secondPart)
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch {
            while (true) {
                lazyListState.autoScroll()
            }
        }
    }
}

private fun <T : Any> List<T>.mapAutoScrollItem(): List<AutoScrollItem<T>> {
    val newList = this.map { AutoScrollItem(data = it) }.toMutableList()
    var index = 0
    if (this.size < REQUIRED_CARD_COUNT) {
        while (newList.size != REQUIRED_CARD_COUNT) {
            if (index > this.size - 1) {
                index = 0
            }

            newList.add(AutoScrollItem(data = this[index]))
            index++
        }
    }
    return newList
}

suspend fun ScrollableState.autoScroll(
    animationSpec: AnimationSpec<Float> = tween(durationMillis = 800, easing = LinearEasing)
) {
    var previousValue = 0f
    scroll(MutatePriority.PreventUserInput) {
        animate(0f, SCROLL_DX, animationSpec = animationSpec) { currentValue, _ ->
            previousValue += scrollBy(currentValue - previousValue)
        }
    }
}

@Composable
fun ListItem(modifier: Modifier = Modifier, itemText: String = "") {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(text = itemText)
    }
}