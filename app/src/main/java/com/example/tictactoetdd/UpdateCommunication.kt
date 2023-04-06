package com.example.tictactoetdd

interface UpdateCommunication : Communication<CellUi> {
    class Base : Communication.Base<CellUi>(), UpdateCommunication {

    }
}