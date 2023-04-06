package com.example.tictactoetdd

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

class MainActivity : AppCompatActivity() {

    private val viewModel =
        MainViewModel(ResultCommunication.Base(), UpdateCommunication.Base(), MainInteractor.Base())

    private val viewsList = mutableMapOf<CellID, ImageButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        var index = 0
        val listOfCellId = CellID.values()
        gridLayout.children.forEach {
            viewsList[listOfCellId[index]] = it as ImageButton
            index++
        }

        viewsList.forEach { (cellId, imageButton) ->
            imageButton.setOnClickListener {
                viewModel.tap(cellId)
            }
        }

        val textView = findViewById<TextView>(R.id.resultTextView)

        viewModel.observe(this) {
            textView.text = it
        }
        viewModel.observeResult(this) {
            it.show(viewsList)
        }

        val newGame = findViewById<View>(R.id.newGameButton)
        newGame.setOnClickListener {
            viewModel.newGame()
        }
    }
}