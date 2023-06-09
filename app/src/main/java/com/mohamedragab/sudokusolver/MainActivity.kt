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
import androidx.core.content.ContextCompat
class MainActivity : AppCompatActivity() {
    private val allGridTextView :MutableList<TextView> = mutableListOf()
    private val theMinimumNumber = 17
    var wherePointerStand :TextView ?= null
    lateinit var resetBT :Button
    lateinit var solveBT :Button
    var foundSolution:Boolean = false
    lateinit var listOfCorrectNumberInSudoku : List<Int>
    var numberInserted = 0
    var isCellsItOk = true
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
    var allCell :Array<Array<Int>> = arrayOf(
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
                var newtextView =  TextView(this)
                var params = GridLayout.LayoutParams()
                params.width = 0
                params.height = GridLayout.LayoutParams.WRAP_CONTENT
                params.columnSpec = GridLayout.spec(col , 1f )
                params.rowSpec =GridLayout.spec(row ,1f)
                newtextView.layoutParams = params
                newtextView.gravity = Gravity.CENTER
                newtextView.setOnClickListener {onClickText(newtextView )}
                newtextView.setText("")
                newtextView.id = allIds[i]
                allGridTextView.add(newtextView.findViewById(allIds[i++]))
                val textColor = ContextCompat.getColor(this, R.color.black)
                newtextView.setTextColor(textColor)
                newtextView.textSize = resources.getDimension(R.dimen.minTextSize)
                newtextView.setBackgroundResource(R.drawable.textview_line_strock)
                mygrid.addView(newtextView)
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    private fun onClickSolve (){
        if(numberInserted < theMinimumNumber )
        {
            Toast.makeText(applicationContext,"at least should insert 17 number ",Toast.LENGTH_SHORT).show()
        }
        else if (isCellsItOk == false  )
        {
            Toast.makeText(applicationContext,"you should delete cell has red color. It wrong",Toast.LENGTH_SHORT).show()
        }
        else
        {
            runSoudkoSolver(allCell)
            if(listOfCorrectNumberInSudoku.isEmpty() == true )
            {
                Toast.makeText(applicationContext,"you can't. \nThe entry data wrong",Toast.LENGTH_SHORT).show()
            }
            else
            {
                var indxAllGridLayout = 0
                var indxSolution = 0
                for (i in 0..8) {
                    for (j in 0..8) {
                        val lastElementInTextView = allGridTextView[indxAllGridLayout]
                        val newElementInTextView = listOfCorrectNumberInSudoku.get(indxSolution)
                        allCell[i][j]=newElementInTextView
                        if(lastElementInTextView.text.toString() == "") {
                            val textColor = ContextCompat.getColor(this, R.color.Grean)
                            lastElementInTextView.setTextColor(textColor)
                            lastElementInTextView.text = newElementInTextView.toString()
                        }
                        indxAllGridLayout++
                        indxSolution++
                    }
                }
            }
        }
    }
    private fun onClickReset (){
        allCell  = arrayOf(
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
        if(wherePointerStand != null )
        {
            wherePointerStand!!.setBackgroundResource(R.drawable.textview_line_strock)
        }
        wherePointerStand = null
        numberInserted = 0
        for(i in allGridTextView )
        {
            val textColor = ContextCompat.getColor(this, R.color.black)
            i.setTextColor(textColor)
            i.text = ""
        }
    }
    private fun onClickText(v: View){
       v as TextView
       if(wherePointerStand == null )
       {
           wherePointerStand = v
           wherePointerStand!!.setBackgroundResource(R.drawable.textview_line_strock_withcolor)
       }
       else
       {
           wherePointerStand!!.setBackgroundResource(R.drawable.textview_line_strock)
           wherePointerStand = v
           wherePointerStand!!.setBackgroundResource(R.drawable.textview_line_strock_withcolor)
       }


    }

    @SuppressLint("ResourceAsColor")
    fun onClickNumber(v: View){
        v as TextView
        if(wherePointerStand == null )
        {
            Toast.makeText(applicationContext,"please select place ",Toast.LENGTH_SHORT).show()
        }
        else if (isCellsItOk == false )
        {
            if(v.text.toString() == "x")
            {
                isCellsItOk =true
                val idWherePointerStand = wherePointerStand!!.context.resources.getResourceEntryName(wherePointerStand!!.id)
                val indxRow = idWherePointerStand.get(4).digitToInt()
                val indxColumn = idWherePointerStand.get(5).digitToInt()
                val textColor = ContextCompat.getColor(this, R.color.black)
                wherePointerStand!!.setTextColor(textColor)
                allCell[indxRow][indxColumn] = 0
                numberInserted--
                wherePointerStand!!.text = ""
            }
            else
            {
                Toast.makeText(applicationContext,"you should delete cell has red color. It wrong",Toast.LENGTH_SHORT).show()

            }
        }
        else
        {
            val idWherePointerStand = wherePointerStand!!.context.resources.getResourceEntryName(wherePointerStand!!.id)
            val indxRow = idWherePointerStand.get(4).digitToInt()
            val indxColumn = idWherePointerStand.get(5).digitToInt()
            if( v.text.toString()== "x")
            {
                val textColor = ContextCompat.getColor(this, R.color.black)
                wherePointerStand!!.setTextColor(textColor)
                allCell[indxRow][indxColumn] = 0
                numberInserted--
                wherePointerStand!!.text = ""

            }
            else
            {
                allCell[indxRow][indxColumn] = v.text.toString().toInt()
                numberInserted++
                wherePointerStand!!.text = "${v.text}"
                val isNotRebetetdInXY  = checkXAndYLine(allCell , indxRow , indxColumn ) && checkRectangle (allCell , indxRow , indxColumn )
                if(isNotRebetetdInXY == false )
                {
                    val textColor = ContextCompat.getColor(this, R.color.red)
                    wherePointerStand!!.setTextColor(textColor)
                    isCellsItOk = false
                }

            }

        }
    }

    private fun runSoudkoSolver(arr: Array<Array<Int>>){
        listOfCorrectNumberInSudoku = listOf()
        foundSolution = false
        excute(arr, 0, 0)
    }
    private fun excute(arr: Array<Array<Int>>, i: Int, j: Int) {
        if(foundSolution == true  ) return
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
                foundSolution = true
                listOfCorrectNumberInSudoku = lis
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
