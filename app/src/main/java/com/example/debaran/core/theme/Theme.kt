package com.example.debaran.core.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.debaran.core.theme.foregroundColor as foreColor

private val DarkColorPalette = darkColorScheme(
  primary = primaryColor,
  primaryContainer = primaryVariantColor,
  secondary = secondaryTextColorDark,
  secondaryContainer = secondaryTextColorMoreDark,
  background = background,
  onSecondary = secondaryTextColorDark,
  onSurface = secondaryTextColorMoreDark,
)

private val LightColorPalette = lightColorScheme(
  primary = Purple500,
  primaryContainer = Purple700,
  secondary = Teal200
)

interface Attrs {
  val errorButton: Color
  val foregroundColor: Color
}

private class DarkAttrsColor : Attrs {
  override val errorButton: Color = Color(0xFFBB2020)
  override val foregroundColor: Color = foreColor
}

private class DebaranColors {
  val defaults @Composable get() = MaterialTheme.colorScheme
  val custom @Composable get() : Attrs = LocalDebaranColors.current
}

private val LocalDebaranColors = staticCompositionLocalOf<Attrs> { DarkAttrsColor() }

object DebaranTheme {
  val colors @Composable get() = LocalDebaranColors.current
}

@Composable
fun ComposeTestTheme(
  attrsColor: Attrs = DarkAttrsColor(),
  content: @Composable () -> Unit,
) {
  /*val colors = if (darkTheme) {
      DarkColorPalette
  } else {
      LightColorPalette
  }
*/
  MaterialTheme(
    colorScheme = DarkColorPalette,
    typography = Typography,
    shapes = Shapes
  ) {
    CompositionLocalProvider(LocalDebaranColors provides attrsColor) {
      content()
    }
  }
}

@Composable
fun DebaranTheme(content: @Composable () -> Unit) {
  ComposeTestTheme {
    Surface(color = MaterialTheme.colorScheme.background) {
      content()
    }
  }
}
