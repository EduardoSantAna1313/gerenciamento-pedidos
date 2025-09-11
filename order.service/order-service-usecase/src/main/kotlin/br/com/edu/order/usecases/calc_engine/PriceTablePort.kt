package br.com.edu.order.usecases.calc_engine

interface PriceTablePort {
    fun getPriceTable(numOrder: Long): PriceTableResult
}