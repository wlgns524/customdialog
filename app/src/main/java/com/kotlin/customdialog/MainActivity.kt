package com.kotlin.customdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO - 앱이 죽을 경우 Permission 확인
        findViewById<TextView>(R.id.tv_show).setOnClickListener {
            CustomDialog(this@MainActivity, R.style.DialogThemeTransparent, "남남남님으로 부터 온 전화입니다").show()
        }
    }
}