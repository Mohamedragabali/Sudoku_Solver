package com.mohamedragab.sudokusolver

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast

private val allGridTextView :MutableList<TextView> = mutableListOf()
class MainActivity : AppCompatActivity() {
    var wherePointerStand :TextView ?= null
    lateinit var resetBT :Button
    lateinit var solveBT :Button
    var numberInserted = 0
    val allIds= listOf(R.id.indx00 ,R.id.indx01 ,R.id.indx02 ,R.id.indx03 ,R.id.indx04
        ,R.id.indx05,R.id.indx06 ,R.id.indx07 ,R.id.indx08 ,R.id.indx10 ,R.id.indx11
        ,R.id.indx12,R.id.indx13 ,R.id.indx14 ,R.id.indx15 ,R.id.indx16 ,R.id.indx17
        ,R.id.indx18,R.id.indx20 ,R.id.indx21 ,R.id.indx22 ,R.id.indx23 ,R.id.indx24
        ,R.id.indx25,R.id.indx26 ,R.id.indx27 ,R.id.indx28 ,R.id.indx30 ,R.id.indx31
        ,R.id.indx32,R.id.indx33 ,R.id.indx34 ,R.id.indx35 ,R.id.indx36 ,R.id.indx37
        ,R.id.indx38,R.id.indx40 ,R.id.indx41 ,R.id.indx42 ,R.id.indx43 ,R.id.indx44
        ,R.id.indx45,R.id.indx46 ,R.id.indx47 ,R.id.indx48 ,R.id.indx50 ,R.id.indx51
        ,R.id.indx52,R.id.indx53 ,R.id.indx54 ,R.id.indx55 ,R.id.indx56 ,R.id.indx57
        ,R.id.indx58,R.id.indx60 ,R.id.indx61 ,R.id.indx62 ,R.id.indx63 ,R.id.indx64
        ,R.id.indx65,R.id.indx66 ,R.id.indx67 ,R.id.indx68 ,R.id.indx70 ,R.id.indx71
        ,R.id.indx72,R.id.indx73 ,R.id.indx74 ,R.id.indx75 ,R.id.indx76 ,R.id.indx77
        ,R.id.indx78,R.id.indx80 ,R.id.indx81 ,R.id.indx82 ,R.id.indx83 ,R.id.indx84
        ,R.id.indx85,R.id.indx86 ,R.id.indx87 ,R.id.indx88 ,
    )
    @SuppressLint("ResourceType", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val mygrid = findViewById<GridLayout>(R.id.myGrid)


        resetBT = findViewById(R.id.resetBT)
        solveBT = findViewById(R.id.solveBT)
        resetBT.setOnClickListener{onClickReset()}
        solveBT.setOnClickListener{onClickSolve()}

        var i = 0
        for(row in 0..8 )
        {
            for(col in 0..8 )
            {
                var cell =  TextView(this)
                var params = GridLayout.LayoutParams()
                params.width = 0
                params.height = GridLayout.LayoutParams.WRAP_CONTENT
                params.columnSpec = GridLayout.spec(col , 1f )
                params.rowSpec =GridLayout.spec(row ,1f)
                cell.layoutParams = params
                cell.gravity = Gravity.CENTER
                cell.setOnClickListener {onClickText(cell )}
                cell.setText("")
                cell.id = allIds[i]
                allGridTextView.add(cell.findViewById(allIds[i++]))
                cell.setTextColor(R.color.black)
                cell.textSize = resources.getDimension(R.dimen.size)
                cell.setBackgroundResource(R.drawable.textview_line_strock)
                mygrid.addView(cell)
            }
        }



    }

    fun onClickSolve (){
        if(numberInserted < 23 )
        {
            Toast.makeText(applicationContext,"at least should insert 23 number ",Toast.LENGTH_SHORT).show()
        }
        else
        {
            var arr :Array<Array<Int>> = arrayOf(
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0),
                arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0)
            )
            var indxAllGridTextView = 0
            for(row in 0..8 ) {
                for (col in 0..8) {
                    val value =  allGridTextView[indxAllGridTextView++].text.toString()
                    if(value != "")
                    {
                        arr[row][col] = value.toInt()
                    }
                }
            }
            val resultOfSudoku = GetSudokuSolution (arr)
            if( resultOfSudoku.flag ==true )
            {
                Toast.makeText(applicationContext,"you can't solve it ",Toast.LENGTH_SHORT).show()
            }
            else{
                var indxAllGridLayout = 0
                var indxSolution = 0
                for (i in 0..8) {
                    for (j in 0..8) {
                        allGridTextView[indxAllGridLayout++].text = resultOfSudoku.solution[indxSolution++].toString()
                    }
                }
            }
        }
    }
    fun onClickReset (){
        wherePointerStand = null
        numberInserted = 0
        for(i in allGridTextView )
        {
            i.text = ""
        }
    }
   fun onClickText(v: View){
        wherePointerStand = v as TextView
    }
    public fun onClickNumber(v: View){
        if(wherePointerStand == null )
        {
            Toast.makeText(applicationContext,"please select place ",Toast.LENGTH_SHORT).show()
        }
        else
        {
            numberInserted++
            wherePointerStand!!.text = "${(v as TextView).text.toString()}"
        }
    }
}

class GetSudokuSolution( arr: Array<Array<Int>>) {
    lateinit var solution: List<Int>
    var flag = true

    init {
        this.excute(arr, 0, 0)
        if (flag) solution = listOf()
    }

    private fun excute(arr: Array<Array<Int>>, i: Int, j: Int) {
        if (flag == false) return
        if (i == 9 || j == 9) return
        else if (arr[i][j] == 0) {
            arr[i][j] = 1; excute(arr, i, j)
            arr[i][j] = 2; excute(arr, i, j)
            arr[i][j] = 3; excute(arr, i, j)
            arr[i][j] = 4; excute(arr, i, j)
            arr[i][j] = 5; excute(arr, i, j)
            arr[i][j] = 6; excute(arr, i, j)
            arr[i][j] = 7; excute(arr, i, j)
            arr[i][j] = 8; excute(arr, i, j)
            arr[i][j] = 9; excute(arr, i, j)
            arr[i][j] = 0
            return
        } else if (checkRectangle(arr, i, j) && checkXAndYLine(arr, i, j)) {

            if (i == j && i == 8) {
                val lis = mutableListOf<Int>()
                for (a in arr) {
                    for (b in a) {
                        lis.add(b)
                    }
                }
                solution = lis
                flag = false
                return
            }
            excute(arr, i, j + 1)
            if (j <= 7) return
            excute(arr, i + 1, 0)
        }
    }

    private fun checkRectangle(arr: Array<Array<Int>>, x: Int, y: Int): Boolean {
        val firstRange = 0..2
        val secondRange = 3..5
        val thiredRange = 6..8
        return if (x in firstRange && y in firstRange) checkRectangle(arr, firstRange, firstRange)
        else if (x in firstRange && y in secondRange) checkRectangle(arr, firstRange, secondRange)
        else if (x in firstRange && y in thiredRange) checkRectangle(arr, firstRange, thiredRange)
        else if (x in secondRange && y in firstRange) checkRectangle(arr, secondRange, firstRange)
        else if (x in secondRange && y in secondRange) checkRectangle(arr, secondRange, secondRange)
        else if (x in secondRange && y in thiredRange) checkRectangle(arr, secondRange, thiredRange)
        else if (x in thiredRange && y in firstRange) checkRectangle(arr, thiredRange, firstRange)
        else if (x in thiredRange && y in secondRange) checkRectangle(arr, thiredRange, secondRange)
        else checkRectangle(arr, thiredRange, thiredRange)
    }

    private fun checkRectangle(arr: Array<Array<Int>>, rangeX: IntRange, rangeY: IntRange): Boolean {
        val number = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (i in rangeX) {
            for (j in rangeY) {
                if (++number[arr[i][j]] == 2 && arr[i][j] != 0) return false
            }
        }
        return true
    }

    private fun checkXAndYLine(arr: Array<Array<Int>>, x: Int, y: Int): Boolean {
        val number = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0..8) {
            if (++number[arr[x][i]] == 2 && arr[x][i] != 0) {
                return false
            }
        }
        val number2 = arrayOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)
        for (i in 0..8) {
            if (++number2[arr[i][y]] == 2 && arr[i][y] != 0) {

                return false
            }
        }
        return true
    }
}