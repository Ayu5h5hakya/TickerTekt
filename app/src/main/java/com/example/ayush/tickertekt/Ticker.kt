package com.example.ayush.tickertekt

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * Created by Ayush on 10/20/2017.
 */
class Ticker : View {

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

    }

    private fun initAttributes(attributeSet: AttributeSet?, defStyleAttr: Int) {

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

    }
}