package com.perf.runners.soap

import com.perf.Result
import com.perf.runners.AbstractPerformanceService

class StockQuotePerformanceService extends AbstractPerformanceService {

    net.webservicex.StockQuoteSoap stockQuoteClient

    com.perf.Result performTest() {
        println 'performaing test StockQuotePerformanceService'
        def duration = benchmark {
            String quote = stockQuoteClient.getQuote(randomStock)
            println quote
        }

        new Result(executionTime: duration, testName: 'Stock Quote Performance Service')
    }

    private String getRandomStock() {
        List stocks = ['BBY', 'AAPL', 'MSFT', 'UNH', 'AA','DLTA','AAA', 'XYXYXY']
        //println stocks.getAt(new Random().nextInt(stocks.size()))
        return stocks.getAt(new Random().nextInt(stocks.size()))
    }
}
