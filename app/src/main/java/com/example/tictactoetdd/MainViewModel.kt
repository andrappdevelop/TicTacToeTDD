package com.example.tictactoetdd

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val communicationResult: ResultCommunication,
    private val updateCommunication: UpdateCommunication,
    private val interactor: MainInteractor
) : ViewModel() {

    init {
        val result = interactor.init()
        result.map(communicationResult, updateCommunication)
    }

    fun observe(owner: LifecycleOwner, observer: Observer<String>) {
        communicationResult.observe(owner, observer)
    }

    fun observeResult(owner: LifecycleOwner, observer: Observer<CellUi>) {
        updateCommunication.observe(owner, observer)
    }

    fun tap(cellID: CellID) {
        val result = interactor.handle(cellID)
        result.map(communicationResult, updateCommunication)
    }

    fun newGame() {
        val result = interactor.reset()
        result.map(communicationResult, updateCommunication)
    }
}