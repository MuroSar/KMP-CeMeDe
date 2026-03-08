package com.cemede.cemede.presentation.util

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.lazy.LazyListState

object AnimationUtils {
    suspend fun LazyListState.smoothScrollTo(targetIndex: Int) {
        // Buscamos si el ítem ya está renderizado en memoria
        val visibleItem = layoutInfo.visibleItemsInfo.find { it.index == targetIndex }

        if (visibleItem != null) {
            // Si está cerca, hacemos un scroll milimétrico y muy suave
            animateScrollBy(
                value = visibleItem.offset.toFloat(),
                animationSpec =
                    tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing,
                    ),
            )
        } else {
            // Si está muy lejos (ej. al final del día), evitamos el mareo visual.
            // Saltamos sin animar hasta 2 lugares antes, y animamos el tramo final.
            val snapTarget = (targetIndex - 2).coerceAtLeast(0)
            scrollToItem(snapTarget)

            // El tramo corto final se ve súper natural
            animateScrollToItem(targetIndex)
        }
    }
}
