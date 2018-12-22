package com.tide.barsaround.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/*
* @param enter An animation or animator resource ID used for the enter animation on the
*              view of the fragment being added or attached.
* @param exit An animation or animator resource ID used for the exit animation on the
*             view of the fragment being removed or detached.
* @param popEnter An animation or animator resource ID used for the enter animation on the
*                 view of the fragment being readded or reattached caused by
*                 {@link FragmentManager#popBackStack()} or similar methods.
* @param popExit An animation or animator resource ID used for the enter animation on the
*                view of the fragment being removed or detached caused by
*                {@link FragmentManager#popBackStack()} or similar methods.
*/
inline fun FragmentManager.inTransaction(
    func: FragmentTransaction.() -> Unit,
    enter: Int = 0,
    exit: Int = 0,
    popEnter: Int = 0,
    popExit: Int = 0
): Int {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
    fragmentTransaction.func()
    return fragmentTransaction.addToBackStack(null).commit()
}
