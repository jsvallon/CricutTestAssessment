package com.circut.project

/*
* TicTacWin: Design an algorithm to figure out if someone has won a game of tic-tac-toe. The algorithm should be written in Kotlin.
* */


/*This Algo pass all the test cases in LeetCode
* 1275. Find Winner on a Tic Tac Toe Game
* */
fun tictactoe(moves: Array<IntArray>): String {
        val board = Array(3) { CharArray(3) { ' ' } } // Initialize 3x3 game board

        // Process moves
        for ((index, move) in moves.withIndex()) {
            val player = if (index % 2 == 0) 'X' else 'O' // Alternate between players
            val (row, col) = move
            board[row][col] = player
        }

        // Check for win conditions
        fun checkWin(player: Char): Boolean {
            // Check rows and columns
            for (i in 0 until 3) {
                if ((0 until 3).all { board[i][it] == player } ||
                    (0 until 3).all { board[it][i] == player }) {
                    return true
                }
            }
            // Check diagonals
            return (0 until 3).all { board[it][it] == player } ||
                    (0 until 3).all { board[it][2 - it] == player }
        }

        // Check for win or tie
        return when {
            checkWin('X') -> "A"
            checkWin('O') -> "B"
            moves.size == 9 -> "Draw"
            else -> "Pending"
        }
    }


fun checkWin(board: Array<Array<Char>>): Boolean {
    val n = board.size
    var rows: Array<Int>
    var cols: Array<Int>
    var diag = 0
    var antiDiag = 0

    for (i in 0 until n) {
        rows = Array(n) { 0 }
        cols = Array(n) { 0 }
        for (j in 0 until n) {
            if (board[i][j] == 'X') {
                rows[j]++
                cols[i]++
                if (i == j) diag++
                if (i + j == n - 1) antiDiag++
            }

            if (rows[j] == n || cols[i] == n || diag == n || antiDiag == n) {
                return true
            }
        }
    }
    return false
}

// Usage
val board = arrayOf(
    arrayOf('X', 'O', 'X'),
    arrayOf('O', 'X', 'O'),
    arrayOf('X', 'O', 'X')
)

fun main() {

    //Algo 1
    println(checkWin(board)) // Output: true

    //Algo 2
    var moves = arrayOf(
        intArrayOf(0, 0), intArrayOf(0, 1), intArrayOf(1, 1),
        intArrayOf(0, 2), intArrayOf(2, 2)
    )
    println(tictactoe(moves)) // Output: A

    moves = arrayOf(
        intArrayOf(0, 0), intArrayOf(1, 1), intArrayOf(0, 1),
        intArrayOf(0, 2), intArrayOf(1, 0), intArrayOf(2, 0)
    )
    println(tictactoe(moves)) // Output: B

    moves = arrayOf(
        intArrayOf(0, 0), intArrayOf(1, 1), intArrayOf(2, 0),
        intArrayOf(1, 0), intArrayOf(1, 2), intArrayOf(2, 1),
        intArrayOf(0, 1), intArrayOf(0, 2), intArrayOf(2, 2)
    )
    println(tictactoe(moves)) // Output: Draw
}

