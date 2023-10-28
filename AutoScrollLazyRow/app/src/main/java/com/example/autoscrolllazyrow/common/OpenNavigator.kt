package com.example.autoscrolllazyrow.common

sealed class OpenNavigator {
    object None: OpenNavigator()
    object Open: OpenNavigator()
}
