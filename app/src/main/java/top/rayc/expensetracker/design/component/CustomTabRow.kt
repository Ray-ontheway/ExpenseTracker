package top.rayc.expensetracker.design.component

import androidx.compose.ui.layout.Measurable
import kotlin.math.min

fun measureTabRow(
    measureables: List<Measurable>,
    minWidth: Int,
): Int {
    val widths = measureables.map {
        it.minIntrinsicWidth(Int.MAX_VALUE)
    }
    var width = widths.max() * measureables.size
    measureables.forEach {
        width += it.minIntrinsicWidth(Int.MAX_VALUE)
    }
    return min(width, minWidth)
}