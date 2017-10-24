package com.example.ayush.tickertekt

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import java.lang.ref.WeakReference
import java.util.*
import kotlin.properties.Delegates

/**
 * Created by Ayush on 10/20/2017.
 */
class Ticker : View {

    var animationTimer: Timer? = null
    var mHandler : AnimationHandler? = null

    var textPaint: Paint? = null
    var circlePath: Path? = null

    var start1: Float by Delegates.observable(200f) { _, _, _ -> mHandler?.obtainMessage(1)?.sendToTarget() }
    var start2: Float by Delegates.observable(200f) {
        _, _, newValue ->
        kotlin.run {
            if (newValue == 0f) animationTimer?.cancel()
            mHandler?.obtainMessage(1)?.sendToTarget()
        }

    }

    var current = 1
    var next = 2
    var delta1 = -180f
    var textSize = 180f

    constructor(context: Context) : this(context, null) { initialize(null, 0) }
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) { initialize(attributeSet, 0) }
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) { initialize(attributeSet, defStyleAttr) }

    private fun initialize(attributeSet: AttributeSet?, defStyleAttr: Int) {

        initAttributes(attributeSet, defStyleAttr)
        initPaint()
        initPath()

        mHandler = AnimationHandler(this)

        animationTimer = Timer()

    }

    private fun tickView() {
        if (start1 == delta1) {
            start1 = delta1
            if (start2 != delta1) start2 -= 10f
            else {
                start2 = 200f
                next += 1
            }
        } else start1 -= 10f
    }

    private fun initPath() {

        circlePath = Path()
        circlePath?.addArc(RectF(-300f, -300f, 300f, 300f), 0f, 360f)

    }

    private fun initPaint() {

        textPaint = Paint()
        textPaint?.textSize = textSize
        textPaint?.color = Color.RED

    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.translate(width / 2f, height / 2f)
        canvas?.clipRect(-250f, -150f, 350f, 50f)
        if (start1 == delta1) canvas?.drawText(next.toString().toCharArray(), 0, next.toString().length, -40f, start2, textPaint)
        else canvas?.drawText(current.toString().toCharArray(), 0, current.toString().length, -40f, start1, textPaint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                animationTimer = Timer()
                animationTimer?.schedule(object : TimerTask() {

                    override fun run() {
                        tickView()

                    }
                },0, 10)
            }

        }
        return true
    }

    companion object {

        class AnimationHandler(instance : Ticker) : Handler() {

            val weakReference = WeakReference<Ticker>(instance)

            override fun handleMessage(msg: Message?) {

                val ticker = weakReference.get()
                ticker?.invalidate()
            }


        }

    }
}