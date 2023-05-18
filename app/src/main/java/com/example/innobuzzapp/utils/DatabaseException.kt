package com.example.innobuzzapp.utils

data class DatabaseException(override val message: String) : Exception(message)