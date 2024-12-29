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
package eu.qwsome.xapi.response;


import java.time.Instant;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public abstract class AbstractTradeRecord {

  private final double closePrice;
  private final boolean closed;
  private final int cmd;
  private final String comment;
  private final String customComment;
  private final double commission;
  private final long order;
  private final long order2;
  private final double volume;
  private final double marginRate;
  private final double openPrice;
  private final String symbol;
  private final double storage;
  private final int digits;


  private final Instant openTime;
  private final Instant closeTime;
  private final Instant expiration;
  private final long position;
  private final double profit;
  private final double sl;
  private final double tp;


  protected AbstractTradeRecord(final JSONObject json) {
    this.closePrice = json.getDouble("close_price");
    this.closed = json.getBoolean("closed");
    this.cmd = json.getInt("cmd");
    this.comment = json.optString("comment");
    this.customComment = json.optString("customComment");
    this.commission = json.getDouble("commission");
    this.order = json.getLong("order");
    this.order2 = json.getLong("order2");
    this.volume = json.getDouble("volume");
    this.marginRate = json.getDouble("margin_rate");
    this.openPrice = json.getDouble("open_price");
    this.symbol = json.getString("symbol");
    this.storage = json.getDouble("storage");
    this.digits = json.getInt("digits");

    final var openTime = json.getLong("open_time");
    this.openTime = Instant.ofEpochMilli(openTime);

    final var closeTime = json.optLongObject("close_time", null);
    this.closeTime = closeTime == null ? null : Instant.ofEpochMilli(closeTime);

    final var expiration1 = json.optLongObject("expiration", null);
    this.expiration = expiration1 == null ? null : Instant.ofEpochMilli(expiration1);

    this.position = json.getLong("position");

    this.profit = json.optDouble("profit");

    this.sl = json.getDouble("sl");
    this.tp = json.getDouble("tp");
  }
}
