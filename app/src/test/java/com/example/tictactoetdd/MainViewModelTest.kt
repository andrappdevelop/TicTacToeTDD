package com.example.tictactoetdd

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun `test player one win`() {
        val communicationResult = TestCommunication()
        val communicationUpdate = TestUpdateCommunication()
        val interactor = MainInteractor.Base()
        val viewModel = MainViewModel(communicationResult, communicationUpdate, interactor)

        viewModel.tap(CellID.UL)
        assertEquals("Player 2, go!", communicationResult.text)
        assertEquals(CellUi.X(CellID.UL), communicationUpdate.value)

        viewModel.tap(CellID.UM)
        assertEquals("Player 1, go!", communicationResult.text)
        assertEquals(CellUi.O(CellID.UM), communicationUpdate.value)

        viewModel.tap(CellID.MM)
        assertEquals("Player 2, go!", communicationResult.text)
        assertEquals(CellUi.X(CellID.MM), communicationUpdate.value)

        viewModel.tap(CellID.MR)
        assertEquals("Player 1, go!", communicationResult.text)
        assertEquals(CellUi.O(CellID.MR), communicationUpdate.value)

        viewModel.tap(CellID.DR)
        assertEquals("Player 1 won!", communicationResult.text)
        assertEquals(CellUi.X(CellID.DR), communicationUpdate.value)

        assertEquals(5, communicationResult.count)
        assertEquals(5, communicationUpdate.count)

        viewModel.tap(CellID.DL)
        assertEquals(5, communicationResult.count)
        assertEquals(5, communicationUpdate.count)
    }

    @Test
    fun `test player one double tap`() {
        val communicationResult = TestCommunication()
        val communicationUpdate = TestUpdateCommunication()
        val interactor = MainInteractor.Base()
        val viewModel = MainViewModel(communicationResult, communicationUpdate, interactor)

        viewModel.tap(CellID.UL)
        assertEquals("Player 2, go!", communicationResult.text)
        assertEquals(1, communicationResult.count)
        assertEquals(CellUi.X(CellID.UL), communicationUpdate.value)
        assertEquals(1, communicationUpdate.count)

        viewModel.tap(CellID.UL)
        assertEquals(1, communicationResult.count)
        assertEquals(1, communicationUpdate.count)
    }

    class TestCommunication : ResultCommunication {

        var count = 0

        var text = ""

        override fun map(data: String) {
            text = data
            count++
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<String>) = Unit
    }

    class TestUpdateCommunication : UpdateCommunication {

        var value: CellUi = CellUi.Empty

        var count = 0

        override fun map(data: CellUi) {
            value = data
            count++
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<CellUi>) = Unit
    }
}