/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 Lukáš Kvídera
 *
 * Permission is hereby granted, free of charge, to any person jsontaining a copy of
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
package eu.qwsome.xapi.stream.subscription.tick;


import java.time.Instant;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class STickRecord {
  private final double ask;
  private final double bid;
  private final Integer askVolume;
  private final Integer bidVolume;
  private final double high;
  private final double low;
  private final double spreadRaw;
  private final double spreadTable;
  private final String symbol;
  private final int quoteId;
  private final int level;
  private final Instant timestamp;


  public STickRecord(final JSONObject json) {
    this.ask = json.getDouble("ask");
    this.bid = json.getDouble("bid");

    this.spreadRaw = json.getDouble("spreadRaw");
    this.spreadTable = json.getDouble("spreadTable");

    this.askVolume = json.getInt("askVolume");
    this.bidVolume = json.getInt("bidVolume");

    this.high = json.getDouble("high");
    this.low = json.getDouble("low");

    this.symbol = json.getString("symbol");
    this.timestamp = Instant.ofEpochMilli(json.getLong("timestamp"));
    this.level = json.getInt("level");
    this.quoteId = json.getInt("quoteId");
  }
}