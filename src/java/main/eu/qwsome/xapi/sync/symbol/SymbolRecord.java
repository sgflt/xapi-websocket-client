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
package eu.qwsome.xapi.sync.symbol;


import java.time.Instant;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class SymbolRecord {

  private final double ask;
  private final double bid;
  private final String currency;
  private final String currencyProfit;
  private final String description;
  private final int instantMaxVolume;
  private final double high;
  private final double low;
  private final String symbol;
  private final Instant time;
  private final int type;
  private final String groupName;
  private final String categoryName;
  private final boolean longOnly;
  private final Instant starting;
  private final Instant expiration;
  private final int stepRuleId;
  private final int stopsLevel;
  private final double lotMax;
  private final double lotMin;
  private final double lotStep;
  private final int precision;
  private Long contractSize;
  private Long initialMargin;
  private double marginHedged;
  private final boolean marginHedgedStrong;
  private Long marginMaintenance;
  private final MarginMode marginMode;
  private final double percentage;
  private final ProfitMode profitMode;
  private final double spreadRaw;
  private final double spreadTable;
  private final boolean swapEnable;
  private final boolean shortSelling;
  private final double swapLong;
  private final double swapShort;
  private final SwapType swapType;
  private final SwapRolloverType swapRollover;
  private final double tickSize;
  private final double tickValue;
  private final int quoteId;
  private final String timeString;
  private final double leverage;
  private final boolean currencyPair;
  private final boolean trailingEnabled;


  public SymbolRecord(final JSONObject e) {
    this.ask = e.getDouble("ask");
    this.bid = e.getDouble("bid");
    this.categoryName = e.getString("categoryName");
    this.currency = e.getString("currency");
    this.description = e.getString("description");
    final var expirationMillis = e.optLongObject("expiration", null);
    this.expiration = expirationMillis == null ? null : Instant.ofEpochMilli(expirationMillis);
    this.groupName = e.getString("groupName");
    this.high = e.getDouble("high");
    this.instantMaxVolume = e.getInt("instantMaxVolume");
    this.longOnly = e.getBoolean("longOnly");
    this.lotMax = e.getDouble("lotMax");
    this.lotMin = e.getDouble("lotMin");
    this.low = e.getDouble("low");
    this.precision = e.getInt("precision");
    this.stepRuleId = e.getInt("stepRuleId");
    final var startingMillis = e.optLongObject("starting", null);
    this.starting = startingMillis == null ? null : Instant.ofEpochMilli(startingMillis);
    this.stopsLevel = e.getInt("stopsLevel");
    this.symbol = e.getString("symbol");
    this.quoteId = e.getInt("quoteId");
    this.currencyProfit = e.getString("currencyProfit");
    final var timeMillis = e.getLong("time");
    this.time = Instant.ofEpochMilli(timeMillis);
    this.timeString = e.getString("timeString");
    this.type = e.getInt("type");

    if (e.has("contractSize")) {
      this.contractSize = e.getLong("contractSize");
    }
    if (e.has("initialMargin")) {
      this.initialMargin = e.getLong("initialMargin");
    }
    this.lotStep = e.getDouble("lotStep");
    if (e.has("marginHedged")) {
      this.marginHedged = e.getDouble("marginHedged");
    }
    this.marginHedgedStrong = (Boolean) e.get("marginHedgedStrong");
    if (e.has("marginMaintenance")) {
      this.marginMaintenance = e.getLong("marginMaintenance");
    }
    this.marginMode = new MarginMode(e.getInt("marginMode"));
    this.percentage = e.getDouble("percentage");
    this.profitMode = new ProfitMode(e.getInt("profitMode"));
    this.shortSelling = e.getBoolean("shortSelling");
    this.swapEnable = e.getBoolean("swapEnable");
    this.swapLong = e.getDouble("swapLong");
    this.swapShort = e.getDouble("swapShort");
    this.swapType = new SwapType(e.getInt("swapType"));
    this.swapRollover = new SwapRolloverType(e.getInt("swap_rollover3days"));
    this.tickSize = e.getDouble("tickSize");
    this.tickValue = e.getDouble("tickValue");
    this.currencyPair = e.getBoolean("currencyPair");
    this.leverage = e.getDouble("leverage");
    this.spreadRaw = e.getDouble("spreadRaw");
    this.spreadTable = e.getDouble("spreadTable");
    this.trailingEnabled = e.getBoolean("trailingEnabled");
  }
}
