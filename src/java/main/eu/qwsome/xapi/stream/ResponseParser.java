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
package eu.qwsome.xapi.stream;

import org.json.JSONObject;

import eu.qwsome.xapi.error.XtbApiException;
import eu.qwsome.xapi.stream.subscription.balance.SBalanceRecord;
import eu.qwsome.xapi.stream.subscription.candle.SCandleRecord;
import eu.qwsome.xapi.stream.subscription.keepalive.SKeepAliveRecord;
import eu.qwsome.xapi.stream.subscription.news.SNewsRecord;
import eu.qwsome.xapi.stream.subscription.profit.SProfitRecord;
import eu.qwsome.xapi.stream.subscription.tick.STickRecord;
import eu.qwsome.xapi.stream.subscription.trade.STradeRecord;
import eu.qwsome.xapi.stream.subscription.trade.status.STradeStatusRecord;

public class ResponseParser {

  public Object parse(final String command, final JSONObject json) {
    return switch (command) {
      case "tickPrices" -> new STickRecord(json);
      case "trade" -> new STradeRecord(json);
      case "balance" -> new SBalanceRecord(json);
      case "tradeStatus" -> new STradeStatusRecord(json);
      case "profit" -> new SProfitRecord(json);
      case "news" -> new SNewsRecord(json);
      case "keepAlive" -> new SKeepAliveRecord(json);
      case "candle" -> new SCandleRecord(json);
      default -> throw new XtbApiException("Unexpected value: " + command);
    };
  }
}
