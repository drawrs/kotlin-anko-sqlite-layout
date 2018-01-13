package com.example.root.ankoexample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        verticalLayout {
            gravity = Gravity.CENTER_HORIZONTAL
            textView {
                textSize = 40F
                text = "Welcome :)"
            }.lparams {
                width = matchParent
                height = matchParent
                gravity = Gravity.CENTER
            }
        }
    }
}
