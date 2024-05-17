package com.saidatmaca.currencyapp.presentation.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import kotlin.math.roundToInt
 data class DottedShape(
    val step: Dp,
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(Path().apply {
        val stepInPixels = with(density) { step.toPx() }

        if (stepInPixels <= 0f) {
            // Hatalı adım değeri
            return@apply
        }

        val numberOfSteps = (size.width / stepInPixels).roundToInt()
        val actualStep = size.width / numberOfSteps
        val halfDotWidth = actualStep / 2

        for (i in 0 until numberOfSteps) {
            val x = i * actualStep
            addRect(
                Rect(
                    offset = Offset(x = x, y = 0f),
                    size = Size(width = halfDotWidth, height = size.height)
                )
            )
        }
        close()
    })
}