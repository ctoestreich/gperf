package com.perf.runners.soap

import com.perf.Result
import com.perf.runners.AbstractPerformanceService
import net.webservicex.StockQuoteSoap

class StockQuotePerformanceService extends AbstractPerformanceService {

    StockQuoteSoap stockQuoteClient

    Result performTest() {
        String quote = ''
        Boolean isError = false

        def duration = benchmark {
            try {
                quote = stockQuoteClient.getQuote(randomStock)
            } catch (Exception e) {
                isError = true
            }
        }

        new Result(details: quote, isError: (isError || !quote), executionTime: duration, testName: 'Stock Quote Performance Service')
    }

    private String getRandomStock() {
        List stocks = ['BBY', 'AAPL', 'MSFT', 'UNH', 'AA', 'DLTA', 'AAA', 'XYXYXY']
        //println stocks.getAt(new Random().nextInt(stocks.size()))
        return stocks.getAt(new Random().nextInt(stocks.size()))
    }
}
