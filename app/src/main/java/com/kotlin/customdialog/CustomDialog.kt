package com.kotlin.customdialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView

class CustomDialog(context: Context, style: Int, message: String) : Dialog(context, style) {

    private var mView: View? = null
    private var wm: WindowManager? = null
    private var params: WindowManager.LayoutParams? = null


    init {
        setCancelable(false)
        setCanceledOnTouchOutside(false)

        // inflater 를 사용하여 layout 지정
        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = inflate.inflate(R.layout.dialog_custom, null)

        mView?.findViewById<TextView>(R.id.tv_calling_message)?.text = message
        mView?.findViewById<TextView>(R.id.tv_negative)?.setOnClickListener {
            dismiss()
        }
        mView?.findViewById<TextView>(R.id.tv_positive)?.setOnClickListener {
            // TODO - 앱 열기 시 packgeName 추가
//            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("")))
            // 임시 기능으로 인스타그램 스토어로 이동
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.instagram.android")
                )
            )
            dismiss()
        }
        // 윈도우매니저 설정
        wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
            PixelFormat.TRANSLUCENT
        )

        // SDK버전 대응
//        params = WindowManager.LayoutParams(
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.MATCH_PARENT,
//            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                    or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                    or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
//            } else {
//                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
//            }, PixelFormat.TRANSLUCENT
//        )

        // 위치 지정
        params?.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
    }

    override fun onDetachedFromWindow() {
        wm?.removeView(mView)
        super.onDetachedFromWindow()
    }

    override fun show() {
        // 윈도우에 layout 추가
        wm?.addView(mView, params)
        super.show()
    }
}