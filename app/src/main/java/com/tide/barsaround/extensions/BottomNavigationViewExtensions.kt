package com.tide.barsaround.extensions

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.tide.barsaround.R

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = this.getChildAt(0) as BottomNavigationMenuView
    menuView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    for (i in 0 until menuView.childCount) {
        val item = menuView.getChildAt(i) as BottomNavigationItemView
        item.setShifting(false)
        // set once again checked value, so view will be updated
        item.setChecked(item.itemData.isChecked)
    }
}

fun BottomNavigationView.setSelectedViewTextStyle(menuItemId: Int, style: Int) {
    val menuView = findViewById<View>(menuItemId)
    val largeTextView = menuView.findViewById<TextView>(R.id.largeLabel)
    largeTextView?.setTextStyle(style)
}

fun BottomNavigationView.setItemText(menuItemId: Int, text: String) {
    val menuView = findViewById<View>(menuItemId)
    val smallTextView = menuView.findViewById<TextView>(R.id.smallLabel)
    val largeTextView = menuView.findViewById<TextView>(R.id.largeLabel)
    smallTextView?.text = text
    largeTextView?.text = text
}
