package com.example.debaran.core.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp
import com.example.debaran.R
import com.example.debaran.core.theme.Dimen.sp13
import com.example.debaran.core.theme.Dimen.sp14
import com.example.debaran.core.theme.Dimen.sp16
import com.example.debaran.core.theme.Dimen.sp24
import com.example.debaran.core.theme.Dimen.sp25

val jostRegular = FontFamily(
  Font(R.font.jost_regular)
)

val jostMedium = FontFamily(
  Font(R.font.jost_medium)
)

val jostBold = FontFamily(
  Font(R.font.jost_bold)
)

@OptIn(ExperimentalUnitApi::class)
val Typography = Typography(
  headlineLarge = TextStyle(
    fontFamily = jostRegular,
    fontSize = sp25
  ),
  headlineMedium = TextStyle(
    fontFamily = jostMedium,
    fontSize = sp24
  ),
  headlineSmall = TextStyle(
    fontFamily = jostMedium,
    fontWeight = FontWeight.W700,
    fontSize = sp16
  ),
  titleLarge = TextStyle(
    fontFamily = jostBold,
    letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
    fontSize = sp14
  ),
  titleMedium = TextStyle(
    fontFamily = jostBold,
    letterSpacing = TextUnit(0.8f, TextUnitType.Sp),
    fontSize = sp13
  ),
  labelMedium = TextStyle(
    fontFamily = jostMedium,
    fontWeight = FontWeight.W700,
    fontSize = 14.sp
  )
)