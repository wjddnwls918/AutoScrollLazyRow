package com.example.autoscrolllazyrow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.autoscrolllazyrow.common.OpenNavigator
import com.example.autoscrolllazyrow.common.PaymentEntity
import com.example.autoscrolllazyrow.test.CustomModalBottomSheet
import com.example.autoscrolllazyrow.test.MainList
import com.example.autoscrolllazyrow.ui.theme.AutoScrollLazyRowTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CommonViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigator()

        setContent {
            AutoScrollLazyRowTheme {
                // A surface container using the 'background' color from the theme
                Column(modifier = Modifier.fillMaxSize()) {
                    Surface(
                        modifier = Modifier.weight(0.2f),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MainList(modifier = Modifier.fillMaxSize())
                    }

                    var showBottomSheet by remember { mutableStateOf(false) }

                    Button(onClick = { showBottomSheet = true }) {
                        Text(text = "show bottomSheet")
                    }
                    Button(onClick = { showBottomSheet = false }) {
                        Text(text = "hide bottomSheet")
                    }

                    val calculatedHeight by remember {
                        derivedStateOf {
                            val headerHeight = 86
                            val itemHeight = 80 * viewModel.paymentList.value.size

                            headerHeight + itemHeight
                        }
                    }

                    CustomModalBottomSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(
                                min = 122.dp,
                                max = if (calculatedHeight > 600) 600.dp else calculatedHeight.dp
                            ),
                        showBottomSheet = showBottomSheet
                    ) {
                        showBottomSheet = it
                    }
                }
            }
        }
    }

    private fun initNavigator() {
        lifecycleScope.launch {
            viewModel.openActivityNavigator.collect {
                if (it is OpenNavigator.Open) {
                    startActivity(Intent(this@MainActivity, SecondActivity::class.java))
                }
            }
        }
    }
}