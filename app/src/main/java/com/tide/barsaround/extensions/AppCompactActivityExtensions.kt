package com.tide.barsaround.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) =
        supportFragmentManager.inTransaction({ add(frameId, fragment) })

fun AppCompatActivity.addAnimatedFragment(fragment: Fragment, frameId: Int, enter: Int, exit: Int, popEnter: Int, popExit: Int) =
        supportFragmentManager.inTransaction({ add(frameId, fragment) }, enter, exit, popEnter, popExit)

fun AppCompatActivity.replaceAnimatedFragment(fragment: Fragment, frameId: Int, enter: Int, exit: Int, popEnter: Int, popExit: Int) =
        supportFragmentManager.inTransaction({ replace(frameId, fragment) }, enter, exit, popEnter, popExit)

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) =
        supportFragmentManager.inTransaction({ replace(frameId, fragment) })
