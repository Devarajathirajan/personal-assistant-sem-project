package com.example.personalassistant.util

fun removeSpecialCharacters(str: String): Int {
    var result = ""
    for (char in str) {
        if (char in '0'..'9') {
            result += char
        } else if (char == '.') {
            break
        }
    }
    return result.toIntOrNull() ?: 0
}
