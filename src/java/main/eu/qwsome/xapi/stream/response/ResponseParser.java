/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 Lukáš Kvídera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package eu.qwsome.xapi.stream.response;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.BaseResponseRecord;
import eu.qwsome.xapi.stream.records.response.SBalanceRecord;
import eu.qwsome.xapi.stream.records.response.SCandleRecord;
import eu.qwsome.xapi.stream.records.response.SKeepAliveRecord;
import eu.qwsome.xapi.stream.records.response.SNewsRecord;
import eu.qwsome.xapi.stream.records.response.SProfitRecord;
import eu.qwsome.xapi.stream.records.response.STickRecord;
import eu.qwsome.xapi.stream.records.response.STradeRecord;
import eu.qwsome.xapi.stream.records.response.STradeStatusRecord;

public class ResponseParser {

  public BaseResponseRecord parse(final String command, final JSONObject json) {

    final var object = getObject(command);
    if (object != null) {
      object.setFieldsFromJSONObject(json);
    }
    return object;
  }


  @Nullable
  private static BaseResponseRecord getObject(final String command) {
    BaseResponseRecord result = null;
    if (command != null) {
      if (command.equals("tickPrices")) {
        result = new STickRecord();
      } else if (command.equals("trade")) {
        result = new STradeRecord();
      } else if (command.equals("balance")) {
        result = new SBalanceRecord();
      } else if (command.equals("tradeStatus")) {
        result = new STradeStatusRecord();
      } else if (command.equals("profit")) {
        result = new SProfitRecord();
      } else if (command.equals("news")) {
        result = new SNewsRecord();
      } else if (command.equals("keepAlive")) {
        result = new SKeepAliveRecord();
      } else if (command.equals("candle")) {
        result = new SCandleRecord();
      }
    }
    return result;
  }


  public LoginResponse parseLogin(String body) {
    return new LoginResponse(body);
  }


  public TradeTransactionResponse parseTradeTransaction(String body) {
    return new TradeTransactionResponse(body);
  }


  public AllSymbolsResponse parseAllSymbols(final String text) {
    return new AllSymbolsResponse(text);
  }


  public SymbolResponse parseGetSymbol(final String text) {
    return new SymbolResponse(text);
  }


  public TradesResponse parseGetTrades(final String text) {
    return new TradesResponse(text);
  }


  public ChartResponse parseGetChartLastRequest(final String text) {
    return new ChartResponse(text);
  }


  public TradeTransactionStatusResponse parseTradeTransactionStatus(final String text) {
    return new TradeTransactionStatusResponse(text);
  }
}
