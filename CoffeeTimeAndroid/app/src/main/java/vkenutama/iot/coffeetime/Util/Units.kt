package vkenutama.iot.coffeetime.Util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

fun Int.toDp(dpi: Int = 420, size: Int = 160) : Dp{
    return (this.toFloat() / (dpi.toFloat() / 160f)).dp
}

//fun Int.toSp(dpi: Int = 420, size: Int = 160) : Sp {
//    return (this.toFloat() / (dpi.toFloat() / 160f)).sp
//}

