package com.example.tictactoetdd

interface ResultCommunication : Communication<String> {

    class Base : Communication.Base<String>(), ResultCommunication
}