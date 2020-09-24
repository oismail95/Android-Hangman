package com.example.hangman

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

private var drawPos: Int = 0

class HangmanConstruct: View {

    constructor(context: Context, attrs: AttributeSet?): super(context, attrs)

    constructor(context: Context): this(context, null)

    private var paint: Paint = Paint()

    fun display(){
        drawPos = 6
    }

    fun setDraw(){
        drawPos = 0
    }

    fun draw(){
        drawPos++
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //Here is where the hangman gets drawn on canvas
        paint.color = Color.BLUE
        paint.strokeWidth = 15F
        canvas?.drawLine(40F, 700F, 120F, 700F, paint)
        canvas?.drawLine(80F, 700F, 80F, 50F, paint)
        canvas?.drawLine(70F, 50F, 320F, 50F, paint)
        canvas?.drawLine(320F, 40F, 320F, 120F, paint)
        invalidate()

        if(drawPos == 1) {
            paint.color = Color.BLACK
            paint.strokeWidth = 15F
            canvas?.drawCircle(320F, 140F, 40F, paint)
            invalidate()
        }
        else if(drawPos == 2){
            paint.color = Color.BLACK
            paint.strokeWidth = 15F
            canvas?.drawCircle(320F, 140F, 40F, paint)
            canvas?.drawLine(320F, 140F, 320F, 480F, paint)
            invalidate()
        }
        else if(drawPos == 3){
            paint.color = Color.BLACK
            paint.strokeWidth = 15F
            canvas?.drawCircle(320F, 140F, 40F, paint)
            canvas?.drawLine(320F, 140F, 320F, 480F, paint)
            canvas?.drawLine(240F, 280F, 320F, 280F, paint)
            invalidate()
        }
        else if(drawPos == 4){
            paint.color = Color.BLACK
            paint.strokeWidth = 15F
            canvas?.drawCircle(320F, 140F, 40F, paint)
            canvas?.drawLine(320F, 140F, 320F, 480F, paint)
            canvas?.drawLine(240F, 280F, 320F, 280F, paint)
            canvas?.drawLine(320F, 280F, 400F, 280F, paint)
            invalidate()
        }
        else if(drawPos == 5){
            paint.color = Color.BLACK
            paint.strokeWidth = 15F
            canvas?.drawCircle(320F, 140F, 40F, paint)
            canvas?.drawLine(320F, 140F, 320F, 480F, paint)
            canvas?.drawLine(240F, 280F, 320F, 280F, paint)
            canvas?.drawLine(320F, 280F, 400F, 280F, paint)
            canvas?.drawLine(320F, 480F, 240F, 640F, paint)
            invalidate()
        }
        else if(drawPos == 6){
            paint.color = Color.BLACK
            paint.strokeWidth = 15F
            canvas?.drawCircle(320F, 140F, 40F, paint)
            canvas?.drawLine(320F, 140F, 320F, 480F, paint)
            canvas?.drawLine(240F, 280F, 320F, 280F, paint)
            canvas?.drawLine(320F, 280F, 400F, 280F, paint)
            canvas?.drawLine(320F, 480F, 240F, 640F, paint)
            canvas?.drawLine(320F, 480F, 400F, 640F, paint)
            invalidate()
        }
    }
}