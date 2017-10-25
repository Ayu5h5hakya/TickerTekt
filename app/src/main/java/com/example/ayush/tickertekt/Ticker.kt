package com.example.ayush.tickertekt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Interpolator
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator


/**
 * Created by Ayush on 10/20/2017.
 */
class Ticker : View, ValueAnimator.AnimatorUpdateListener {

    private val DEFAULT_TEXT_SIZE = 180f
    private val DEFAULT_TEXT_COLOR = Color.argb(255, 0, 0, 0)
    private val DEFAULT_ANIMATION_DURATION = 1000

    val positionAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 200f)

    var textPaint: Paint? = null

    private var direction: Boolean = false
    private var start1: Float = 200f
    private var numberArray = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    var textSize = DEFAULT_TEXT_SIZE
    var textColor = DEFAULT_TEXT_COLOR
    var duration = DEFAULT_ANIMATION_DURATION
    var animationRunning = true

    constructor(context: Context) : this(context, null) {
        initialize(null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
        initialize(attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        initialize(attributeSet, defStyleAttr)
    }

    private fun initialize(attributeSet: AttributeSet?, defStyleAttr: Int) {

        if (attributeSet != null) initAttributes(attributeSet, defStyleAttr)
        initPaint()
        initPath()

        positionAnimator.duration = duration.toLong()
        positionAnimator.addUpdateListener(this)
        positionAnimator.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator?) {
            }

        })


    }

    private fun initPath() {}

    private fun initPaint() {

        textPaint = Paint()
        textPaint?.textSize = textSize
        textPaint?.color = textColor

    }

    private fun initAttributes(attributeSet: AttributeSet, defStyleAttr: Int) {

        val attrArray = context.obtainStyledAttributes(attributeSet, R.styleable.Ticker, defStyleAttr, 0)
        textSize = attrArray.getFloat(R.styleable.Ticker_textSize, DEFAULT_TEXT_SIZE)
        textColor = attrArray.getColor(R.styleable.Ticker_textColor, DEFAULT_TEXT_COLOR)
        duration = attrArray.getInt(R.styleable.Ticker_duration, DEFAULT_ANIMATION_DURATION)
        attrArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate(width / 2f, height / 2f)
        Log.d("Position", start1.toString())
        canvas?.drawText(numberArray[0].toString().toCharArray(), 0, numberArray[0].toString().length, 0f, start1, textPaint)
        canvas?.restore()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                direction = !direction
                positionAnimator.start()
            }

        }
        return true
    }

    //---------------------------------------------------------------------------------------------->

    override fun onAnimationUpdate(animation: ValueAnimator) {

        val deltacurrent = animation.animatedValue as Float
        start1 = 200f - deltacurrent
        if (!direction) start1 = deltacurrent * -1

        invalidate()

    }

    //---------------------------------------------------------------------------------------------->

}