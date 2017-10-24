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
    var start1 = 200f
    var start2 = 200f
    val current = 1
    val next = 2
    var delta1 = -180f
    val delta2=0f

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
                if (start1 == delta1) {
                    start1 = delta1
                    if (start2!=delta2)
                    {
                        start2 -= 1f
                        mHandler?.obtainMessage(2)?.sendToTarget()
                    }
                }
                else{
                    start1 -= 1f
                    mHandler?.obtainMessage(1)?.sendToTarget()
                }


            }
        }, 1000, 10)

    }

    private fun initPath() {

        circlePath = Path()
        circlePath?.addArc(RectF(-300f,-300f,300f,300f),0f, 360f)

    }

    private fun initPaint() {

        textPaint = Paint()
        textPaint?.textSize=180f
        textPaint?.color = Color.RED

    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.translate(width/2f, height/2f)
        canvas?.clipRect(-250f,-150f,250f,50f)
        canvas?.drawText(current.toString().toCharArray(), 0 ,1, -40f, start1, textPaint)
        if (start1 == delta1) canvas?.drawText(next.toString().toCharArray(), 0 ,1, -40f, start2, textPaint)
    }
}