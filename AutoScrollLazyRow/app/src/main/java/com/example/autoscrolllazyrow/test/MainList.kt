package com.example.autoscrolllazyrow.test

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.autoscrolllazyrow.CommonViewModel

@Composable
fun MainList(modifier: Modifier = Modifier, commonViewModel: CommonViewModel = viewModel()) {
    LazyColumn(modifier = modifier) {
        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f)
                    .clickable {
                        commonViewModel.openSecondActivity()
                    }, contentAlignment = Alignment.Center
            ) {
                Text(text = "1")
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "2")
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "3")
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "4")
            }
        }

        item {
            // auto lazy Row
            AutoScrollingLazyRow(
                list = (1..8).take(4), modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f)
            ) {
                ListItem(
                    modifier = Modifier
                        .size(width = 140.dp, height = 168.dp)
                        .clickable {
                            commonViewModel.openSecondActivity()
                        }, it.toString()
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "5")
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "6")
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "7")
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "8")
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "9")
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .fillParentMaxHeight(0.3f), contentAlignment = Alignment.Center
            ) {
                Text(text = "10")
            }
        }
    }
}