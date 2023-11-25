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


                    CustomModalBottomSheet(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(600.dp),
                        showBottomSheet = showBottomSheet,
                        paymentList = makeDummyData()
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

    private fun makeDummyData(): List<PaymentEntity> {
        return listOf(
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825", true),
            PaymentEntity("신한카드 Deep Dream O…", "본인 9940"),
            PaymentEntity("신한카드 Deep Once Plus", "본인 9940"),
            PaymentEntity("신한은행 신한 주거래 우대통장", "신한은행 2715", false),
            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
            PaymentEntity("Deep Dream 미니언즈", "신한카드 본인 4825"),
            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
            PaymentEntity("농협 자유저축예탁금", "농협 2715", false),
        )
    }
}