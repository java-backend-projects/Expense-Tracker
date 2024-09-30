package ru.sug4chy.repository

interface ExpenseRepository {
    fun lastAddedId(): Result<Long>
    fun save(): Result<Unit>
}