package com.tide.barsaround.ui.common

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import android.view.View

private const val TWO = 2

class SpacingItemDecoration : RecyclerView.ItemDecoration {

    private var spaceLeft: Int = 0

    private var spaceRight: Int = 0

    private var spaceTop: Int = 0

    private var spaceBottom: Int = 0

    private val resources: Resources

    constructor(context: Context) {
        resources = context.resources
    }

    constructor(context: Context, @DimenRes spacing: Int) {
        resources = context.resources
        val mSpacing = getDimensionPixels(spacing)
        spaceBottom = mSpacing
        spaceTop = spaceBottom
        spaceRight = spaceTop
        spaceLeft = spaceRight
    }

    fun setSpaceLeft(spaceLeft: Int) {
        this.spaceLeft = getDimensionPixels(spaceLeft)
    }

    fun setSpaceRight(spaceRight: Int) {
        this.spaceRight = getDimensionPixels(spaceRight)
    }

    fun setSpaceTop(spaceTop: Int) {
        this.spaceTop = getDimensionPixels(spaceTop)
    }

    fun setSpaceBottom(spaceBottom: Int) {
        this.spaceBottom = getDimensionPixels(spaceBottom)
    }

    fun setVerticalSpacing(@DimenRes verticalSpace: Int) {
        val mVerticalSpace = getDimensionPixels(verticalSpace)
        spaceTop = mVerticalSpace / TWO
        spaceBottom = mVerticalSpace / TWO
    }

    fun setHorizontalSpacing(@DimenRes horizontalSpacing: Int) {
        val mHorizontalSpace = getDimensionPixels(horizontalSpacing)
        spaceLeft = mHorizontalSpace / TWO
        spaceRight = mHorizontalSpace / TWO
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(spaceLeft, spaceTop, spaceRight, spaceBottom)
    }

    private fun getDimensionPixels(@DimenRes dimenRes: Int) = resources.getDimensionPixelSize(dimenRes)
}
