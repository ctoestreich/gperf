package com.perf.runners.soap

import com.perf.Result
import com.perf.runners.AbstractPerformanceService
import net.webservicex.StockQuoteSoap

class StockQuotePerformanceService extends AbstractPerformanceService {

    StockQuoteSoap stockQuoteClient

    com.perf.Result performTest() {
        String quote = ''

        def duration = benchmark {
            quote = stockQuoteClient.getQuote(randomStock)
        }

        new Result(details: quote, executionTime: duration, testName: 'Stock Quote Performance Service')
    }

    private String getRandomStock() {
        List stocks = ['BBY', 'AAPL', 'MSFT', 'UNH', 'AA','DLTA','AAA', 'XYXYXY']
        //println stocks.getAt(new Random().nextInt(stocks.size()))
        return stocks.getAt(new Random().nextInt(stocks.size()))
    }
}
