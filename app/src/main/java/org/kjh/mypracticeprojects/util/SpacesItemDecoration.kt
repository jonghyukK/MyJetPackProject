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
class SpacesItemDecoration(context: Context) : ItemDecoration() {
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
        val column = position % 3

        //상하 설정
        if (position == 0 || position == 1 || position == 2) {
            // 첫번 째 줄 아이템
            outRect.top = size10
//            outRect.bottom = size5
        } else {
//            outRect.bottom = size5
        }

        // spanIndex = 0 -> 왼쪽
        // spanIndex = 1 -> 오른쪽
        val lp = view.layoutParams as GridLayoutManager.LayoutParams
        val spanIndex = lp.spanIndex

        outRect.left = column * 5 / 3
        outRect.right = 5 - (column + 1) * 5 / 3
        if (position >= 3) {
            outRect.top = 5
        }

//        if (spanIndex == 0) {
//            //왼쪽 아이템
//            outRect.right = size5
//        } else if (spanIndex == 1) {
//            //오른쪽 아이템
//            outRect.left = size5
//            outRect.right = size5
//        } else if (spanIndex == 2) {
//            outRect.left = size5
//            outRect.right = size10
//        }

//        outRect.top = size10
//        outRect.right = size10
//        outRect.bottom = size10
//        outRect.left = size10
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
        size10 = dpToPx(context, 10)
        size5 = dpToPx(context, 5)
    }
}