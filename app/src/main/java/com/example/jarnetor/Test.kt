package com.example.jarnetor


fun main() {
    var testStr = "subject1EE/Physics - 101"

    for(i in 0..testStr.length){
        if (testStr[i].equals("/")) {
            testStr = testStr.dropLast(1)
            break
        } else {
            testStr = testStr.dropLast(1)
        }
        println(testStr)
    }

    println(testStr)
}