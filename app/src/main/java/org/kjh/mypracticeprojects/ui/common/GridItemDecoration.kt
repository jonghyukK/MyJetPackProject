package org.kjh.mypracticeprojects.ui.common

import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 * MyPracticeProjects
 * Class: SpacesItemDecoration
 * Created by mac on 2021/08/10.
 *
 * Description:
 */
class GridItemDecoration(
    context: Context,
    private val spanCnt: Int
) : ItemDecoration() {

    private val size10: Int
    private val size5 : Int

    override fun getItemOffsets(
        outRect : Rect,
        view    : View,
        parent  : RecyclerView,
        state   : RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        when (spanCnt) {
            3 -> {
                val column = position % 3
                if (position == 0 || position == 1 || position == 2)
                    outRect.top = size10

                outRect.left  = column * 5 / 3
                outRect.right = 5 - (column + 1) * 5 / 3

                if (position >= 3)
                    outRect.top = 5
            }

            2 -> {
                if (position == 0 || position == 1)
                    outRect.top = size10

                val lp = view.layoutParams as GridLayoutManager.LayoutParams
                val spanIndex = lp.spanIndex

                if (spanIndex == 0) {
                    outRect.right = size5
                    outRect.left  = size10
                } else if (spanIndex == 1) {
                    outRect.left  = size5
                    outRect.right = size10
                }

                outRect.bottom = size10
            }
        }
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        )
            .toInt()
    }

    init {
        size10 = dpToPx(context, 10)
        size5  = dpToPx(context, 5)
    }
}