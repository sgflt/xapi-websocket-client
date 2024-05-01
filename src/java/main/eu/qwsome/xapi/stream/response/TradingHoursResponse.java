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


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

@Getter
@ToString
public class TradingHoursResponse extends BaseResponse {

  private final List<String> symbols;
  private final List<List<HoursRecord>> quotes = new LinkedList<List<HoursRecord>>();
  private final List<List<HoursRecord>> trading = new LinkedList<List<HoursRecord>>();


  public TradingHoursResponse(final String body) {
    super(body);

    final var aHR = (JSONArray) this.getReturnData();
    this.symbols = new ArrayList<>(aHR.length());

    for (final var o : aHR) {
      final var ob = (JSONObject) o;
      final var symbol = ob.getString("symbol");
      this.symbols.add(symbol);

      final var qHR = (JSONArray) ob.get("quotes");
      final var quotesList = new ArrayList<HoursRecord>(qHR.length());
      for (final var e : qHR) {
        final var rec = new HoursRecord();
        rec.setFieldsFromJSONObject((JSONObject) e);
        quotesList.add(rec);
      }
      this.quotes.add(quotesList);

      final var tHR = (JSONArray) ob.get("trading");
      final var tradingList = new ArrayList<HoursRecord>(tHR.length());
      for (final var e : tHR) {
        final var rec = new HoursRecord();
        rec.setFieldsFromJSONObject((JSONObject) e);
        tradingList.add(rec);
      }

      this.trading.add(tradingList);
    }
  }
}
