package com.example.ayush.tickertekt

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

/**
 * Created by Ayush on 10/20/2017.
 */
class Ticker : View {

    var mHandler : Handler? = null
    var textPaint : Paint? = null
    var circlePath: Path? = null
    var startX = 0f

    constructor(context : Context) : this(context, null){
        initialize(null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0){
        initialize(attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr){
        initialize(attributeSet, defStyleAttr)
    }

    private fun initialize(attributeSet: AttributeSet?, defStyleAttr: Int){

        initAttributes(attributeSet, defStyleAttr)
        initPaint()
        initPath()

        mHandler = object : Handler() {

            override fun handleMessage(msg: Message) {
                invalidate()
            }
        }

        val t = Timer()
        t.schedule(object : TimerTask() {

            override fun run() {
                startX -= 1f
                mHandler?.obtainMessage(1)?.sendToTarget()

            }
        }, 1000, 50)

    }

    private fun initPath() {

        circlePath = Path()
        circlePath?.addArc(RectF(-300f,-300f,300f,300f),0f, 360f)

    }

    private fun initPaint() {

        textPaint = Paint()
        textPaint?.textSize=36f
        textPaint?.color = Color.RED

    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.translate(width/2f, height/2f)
        canvas?.clipRect(-50,-50,50,50)
        canvas?.drawText("Ayush", 0 , 5, startX, 0f, textPaint)

    }
}