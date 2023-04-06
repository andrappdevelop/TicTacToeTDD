package com.example.tictactoetdd

import android.widget.ImageButton

abstract class CellUi {

    abstract fun map(
        communicationResult: ResultCommunication,
        updateCommunication: UpdateCommunication,
        message: String
    )

    abstract fun show(viewsList: MutableMap<CellID, ImageButton>)

    data class X(private val id: CellID) : CellUi() {
        override fun map(
            communicationResult: ResultCommunication,
            updateCommunication: UpdateCommunication,
            message: String
        ) {
            communicationResult.map(message)
            updateCommunication.map(this)
        }

        override fun show(viewsList: MutableMap<CellID, ImageButton>) {
            viewsList[id]?.setImageResource(R.drawable.x)
        }
    }

    data class O(private val id: CellID) : CellUi() {
        override fun map(
            communicationResult: ResultCommunication,
            updateCommunication: UpdateCommunication,
            message: String
        ) {
            communicationResult.map(message)
            updateCommunication.map(this)
        }

        override fun show(viewsList: MutableMap<CellID, ImageButton>) {
            viewsList[id]?.setImageResource(R.drawable.o)
        }
    }

    object Empty : CellUi() {
        override fun map(
            communicationResult: ResultCommunication,
            updateCommunication: UpdateCommunication,
            message: String
        ) = Unit

        override fun show(viewsList: MutableMap<CellID, ImageButton>) = Unit
    }

    class Reset : CellUi() {
        override fun map(
            communicationResult: ResultCommunication,
            updateCommunication: UpdateCommunication,
            message: String
        ) {
            communicationResult.map(message)
            updateCommunication.map(this)
        }

        override fun show(viewsList: MutableMap<CellID, ImageButton>) {
            viewsList.forEach {
                it.value.setImageResource(0)
            }
        }
    }
}