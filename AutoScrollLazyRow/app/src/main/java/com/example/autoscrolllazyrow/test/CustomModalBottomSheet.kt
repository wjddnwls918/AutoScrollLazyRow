package com.example.autoscrolllazyrow.test

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.autoscrolllazyrow.R
import com.example.autoscrolllazyrow.common.PaymentEntity
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomModalBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean,
    paymentList: List<PaymentEntity> = listOf(),
    setShowBottomSheetState: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        setShowBottomSheetState(false)
                    }
                }
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, 0.dp, 0.dp),
            dragHandle = {
                //none
            }
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(start = 24.dp, end = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "결제수단 변경")

                    IconButton(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                setShowBottomSheetState(false)
                            }
                        }
                    }) {
                        Icon(Icons.Default.Close, contentDescription = "닫기 버튼")
                    }
                }

                LazyColumn(modifier = Modifier.weight(1f)) {
                    itemsIndexed(items = paymentList) { index, item ->
                        PaymentItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .padding(start = 16.dp, end = 16.dp),
                            item
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentItem(modifier: Modifier = Modifier, paymentEntity: PaymentEntity) {
    Surface(modifier = modifier.fillMaxSize()) {

        // 선택 테두리
        if (paymentEntity.isSelected) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .shadow(
                        elevation = 12.dp,
                        spotColor = Color(0x29000000),
                        ambientColor = Color(0x29000000)
                    )
                    .border(
                        width = 2.dp,
                        color = Color(0xFF414E7F),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            ) {

            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 16.dp, top = 18.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                // 텍스트
                Text(text = paymentEntity.crdPdNm)
                Text(text = paymentEntity.paymentDesc)
            }

            // 이미지
            when (paymentEntity.isCard) {
                true -> {
                    Image(
                        modifier = Modifier
                            .width(28.dp)
                            .height(44.dp),
                        painter = painterResource(id = R.drawable.dummy),
                        contentDescription = ""
                    )
                }

                else -> {
                    Image(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.bank_default),
                        contentDescription = ""
                    )
                }
            }
        }

    }
}