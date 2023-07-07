package com.example.debaran.core.theme

import android.content.res.Resources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private val width = Resources.getSystem().displayMetrics.widthPixels
private val height = Resources.getSystem().displayMetrics.heightPixels

private const val SMALL_SCREEN_PX = 480 * 800

private class ResponsiveDimensions(
    private val valueNormalSize: Dp,
    private val valueSmallSize: Dp
) : ReadOnlyProperty<Any?, Dp> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): Dp {
        return when {
            width * height <= SMALL_SCREEN_PX -> valueSmallSize
            else -> valueNormalSize
        }
    }
}

private class ResponsiveTextDimensions(
    private val valueNormalSize: TextUnit,
    private val valueSmallSize: TextUnit
) : ReadOnlyProperty<Any?, TextUnit> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): TextUnit {
        return when {
            width * height <= SMALL_SCREEN_PX -> valueSmallSize
            else -> valueNormalSize
        }
    }
}

object Dimen {
    /* Dp */
    val dp160 by ResponsiveDimensions(160.dp, 120.dp)
    val dp100 by ResponsiveDimensions(100.dp, 80.dp)
    val dp55 by ResponsiveDimensions(55.dp, 45.dp)

    /* Sp */
    val spNormal by ResponsiveTextDimensions(TextUnit.Unspecified, 13.sp)
    val sp25 by ResponsiveTextDimensions(25.sp, 18.sp)
    val sp24 by ResponsiveTextDimensions(24.sp, 17.sp)
    val sp16 by ResponsiveTextDimensions(16.sp, 12.sp)
    val sp14 by ResponsiveTextDimensions(14.sp, 11.sp)
    val sp13 by ResponsiveTextDimensions(13.sp, 10.sp)
}

