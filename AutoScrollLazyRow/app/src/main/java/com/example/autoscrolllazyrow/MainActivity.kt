package com.example.autoscrolllazyrow

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.autoscrolllazyrow.common.OpenNavigator
import com.example.autoscrolllazyrow.test.MainList
import com.example.autoscrolllazyrow.ui.theme.AutoScrollLazyRowTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: CommonViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigator()

        setContent {
            AutoScrollLazyRowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainList(modifier = Modifier.fillMaxSize())
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