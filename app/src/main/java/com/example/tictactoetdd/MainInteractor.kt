package com.example.tictactoetdd

interface MainInteractor {

    fun handle(cellId: CellID): ResultUi

    fun reset(): ResultUi
    fun init(): ResultUi

    class Base : MainInteractor {

        private var currentPlayerIsFirstPlayer = true

        private val dataMap = mutableMapOf<CellID, Int>()

        private var gameOver = false

        init {
            CellID.values().forEach {
                dataMap[it] = 0
            }
        }

        override fun handle(cellId: CellID): ResultUi {
            return if (gameOver) {
                ResultUi("", CellUi.Empty)
            } else {
                if (dataMap[cellId]!! == 0) {
                    dataMap[cellId] = if (currentPlayerIsFirstPlayer) 1 else -1

                    var message = if (currentPlayerIsFirstPlayer) PLAYER_2_GO else PLAYER_1_GO

                    val upRow = dataMap[CellID.UL] == dataMap[CellID.UM] &&
                            dataMap[CellID.UL] == dataMap[CellID.UR] && dataMap[CellID.UL] != 0

                    val midRow = dataMap[CellID.ML] == dataMap[CellID.MM] &&
                            dataMap[CellID.ML] == dataMap[CellID.MR] && dataMap[CellID.ML] != 0

                    val downRow = dataMap[CellID.DL] == dataMap[CellID.DM] &&
                            dataMap[CellID.DL] == dataMap[CellID.DR] && dataMap[CellID.DL] != 0

                    val leftColumn = dataMap[CellID.UL] == dataMap[CellID.ML] &&
                            dataMap[CellID.UL] == dataMap[CellID.DL] && dataMap[CellID.UL] != 0

                    val midColumn = dataMap[CellID.UM] == dataMap[CellID.MM] &&
                            dataMap[CellID.UM] == dataMap[CellID.DM] && dataMap[CellID.UM] != 0

                    val rightColumn = dataMap[CellID.UR] == dataMap[CellID.MR] &&
                            dataMap[CellID.UR] == dataMap[CellID.DR] && dataMap[CellID.UR] != 0

                    val diagonalLeft = dataMap[CellID.UL] == dataMap[CellID.MM] &&
                            dataMap[CellID.UL] == dataMap[CellID.DR] && dataMap[CellID.UL] != 0

                    val diagonalRight = dataMap[CellID.UR] == dataMap[CellID.MM] &&
                            dataMap[CellID.UR] == dataMap[CellID.DL] && dataMap[CellID.UR] != 0

                    if (
                        upRow ||
                        midRow ||
                        downRow ||
                        leftColumn ||
                        midColumn ||
                        rightColumn ||
                        diagonalLeft ||
                        diagonalRight
                    ) {
                        var firstWon = false

                        when {
                            diagonalLeft || diagonalRight -> firstWon = dataMap[CellID.MM] == 1
                            upRow -> firstWon = dataMap[CellID.UM] == 1
                            midRow -> firstWon = dataMap[CellID.MM] == 1
                            downRow -> firstWon = dataMap[CellID.DM] == 1
                            leftColumn -> firstWon = dataMap[CellID.ML] == 1
                            midColumn -> firstWon = dataMap[CellID.MM] == 1
                            rightColumn -> firstWon = dataMap[CellID.MR] == 1
                        }
                        message = if (firstWon) PLAYER_1_WON else PLAYER_2_WON
                        gameOver = true
                    }

                    val cell =
                        if (currentPlayerIsFirstPlayer) CellUi.X(cellId) else CellUi.O(cellId)
                    currentPlayerIsFirstPlayer = !currentPlayerIsFirstPlayer
                    ResultUi(
                        message,
                        cell
                    )
                } else {
                    ResultUi("", CellUi.Empty)
                }
            }
        }

        override fun reset(): ResultUi {
            gameOver = false
            currentPlayerIsFirstPlayer = true
            CellID.values().forEach {
                dataMap[it] = 0
            }
            return ResultUi(PLAYER_1_GO, CellUi.Reset())
        }

        override fun init(): ResultUi {
            return ResultUi(PLAYER_1_GO, CellUi.Reset())
        }

        companion object {
            private const val PLAYER_1_GO = "Player 1, go!"
            private const val PLAYER_2_GO = "Player 2, go!"
            private const val PLAYER_1_WON = "Player 1 won!"
            private const val PLAYER_2_WON = "Player 2 won!"
        }
    }
}

data class ResultUi(
    private val message: String,
    private val cell: CellUi
) {
    fun map(
        communicationResult: ResultCommunication,
        updateCommunication: UpdateCommunication
    ) {
        cell.map(communicationResult, updateCommunication, message)
    }
}