package com.example.ayush.tickertekt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * Created by Ayush on 10/20/2017.
 */
class Ticker : View, ValueAnimator.AnimatorUpdateListener {

    private val DEFAULT_TEXT_SIZE = 180f
    private val DEFAULT_TEXT_COLOR = Color.argb(255, 0, 0, 0)
    private val DEFAULT_ANIMATION_DURATION = 1000

    private lateinit var positionAnimator: ValueAnimator
    private val gapFactor = 1f

    private var textPaint: Paint? = null

    private var start1: Float = 0f
    //--------------TESTING-------------------------->
    private var numberArray = intArrayOf(11, 222, 3, 545656, 655555, 677, 127, 18, 867867869)
    private var index = 0
    //--------------TESTING-------------------------->

    private var textSize = DEFAULT_TEXT_SIZE
    private var textColor = DEFAULT_TEXT_COLOR
    private var duration = DEFAULT_ANIMATION_DURATION
    private var current = numberArray[0].toString()
    private var next = numberArray[1].toString()
    private var textHeight: Int = 0
    private var textWidth: Int = 0

    constructor(context: Context) : this(context, null) {
        initialize(null, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0) {
        initialize(attributeSet, 0)
    }

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        initialize(attributeSet, defStyleAttr)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        var height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)

        when (MeasureSpec.getMode(widthMeasureSpec)) {

            MeasureSpec.EXACTLY -> { }
            MeasureSpec.AT_MOST -> { width = Math.min(width, textWidth) }
            MeasureSpec.UNSPECIFIED -> { width = textWidth }
        }
        when (MeasureSpec.getMode(heightMeasureSpec)) {

            MeasureSpec.EXACTLY -> { }
            MeasureSpec.AT_MOST -> { height = Math.min(height, textHeight) }
            MeasureSpec.UNSPECIFIED -> { width = textWidth }
        }
        setMeasuredDimension(width, height)

        //setupPositionAnimator(height)
    }

    private fun setupPositionAnimator(height: Int) {
        positionAnimator = ValueAnimator.ofFloat(0f, height * gapFactor)
        positionAnimator.duration = duration.toLong()
        positionAnimator.addUpdateListener(this)
    }

    private fun initialize(attributeSet: AttributeSet?, defStyleAttr: Int) {

        if (attributeSet != null) initAttributes(attributeSet, defStyleAttr)
        initPaint()
        calculateTextCenter(current)
        initPath()


    }

    private fun calculateTextCenter(text: String) {

        val textBounds = Rect()
        textPaint?.getTextBounds(text, 0, text.length, textBounds)
        textHeight = textBounds.height()
        textWidth = textBounds.width()

    }

    private fun initPath() {}

    private fun initPaint() {

        textPaint = Paint()
        textPaint?.textSize = textSize
        textPaint?.color = textColor
        textPaint?.textAlign = Paint.Align.CENTER

    }

    private fun initAttributes(attributeSet: AttributeSet, defStyleAttr: Int) {

        val attrArray = context.obtainStyledAttributes(attributeSet, R.styleable.Ticker, defStyleAttr, 0)
        textSize = attrArray.getFloat(R.styleable.Ticker_textSize, DEFAULT_TEXT_SIZE)
        textColor = attrArray.getColor(R.styleable.Ticker_textColor, DEFAULT_TEXT_COLOR)
        duration = attrArray.getInt(R.styleable.Ticker_duration, DEFAULT_ANIMATION_DURATION)
        attrArray.recycle()
    }

    private fun getCurrentY() = textHeight / 2f - start1
    private fun getNextY() = textHeight / 2f + height * gapFactor - start1

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate(width / 2f, height / 2f)
        canvas?.drawText(current.toCharArray(), 0, current.length, 0f, getCurrentY(), textPaint)
        canvas?.drawText(next.toCharArray(), 0, next.length, 0f, getNextY(), textPaint)
        canvas?.restore()

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                index++
                current = numberArray[index % 9].toString()
                next = numberArray[(index + 1) % 9].toString()
                calculateTextCenter(current)
                requestLayout()
                positionAnimator.start()
            }

        }
        return true
    }

    //---------------------------------------------------------------------------------------------->

    override fun onAnimationUpdate(animation: ValueAnimator) {
        start1 = animation.animatedValue as Float
        invalidate()
    }

    //---------------------------------------------------------------------------------------------->

}