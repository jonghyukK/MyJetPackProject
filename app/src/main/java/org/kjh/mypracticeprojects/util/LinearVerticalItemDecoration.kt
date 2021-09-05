package org.kjh.mypracticeprojects.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.util.TypedValue

import androidx.recyclerview.widget.GridLayoutManager

import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 * MyPracticeProjects
 * Class: SpacesItemDecoration
 * Created by mac on 2021/08/10.
 *
 * Description:
 */
class LinearVerticalItemDecoration(context: Context) : ItemDecoration() {
    private val size20: Int
    private val size10: Int
    private val size5: Int

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount

        outRect.bottom = size20
    }

    // dp -> pixel 단위로 변경
    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
            .toInt()
    }

    init {
        size20 = dpToPx(context, 20)
        size10 = dpToPx(context, 10)
        size5 = dpToPx(context, 5)
    }
}