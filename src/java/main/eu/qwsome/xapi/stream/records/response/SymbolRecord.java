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
package eu.qwsome.xapi.stream.records.response;


import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.MarginMode;
import eu.qwsome.xapi.stream.codes.ProfitMode;
import eu.qwsome.xapi.stream.codes.SwapRolloverType;
import eu.qwsome.xapi.stream.codes.SwapType;

@Getter
@Setter
@ToString
public class SymbolRecord implements BaseResponseRecord {

  private double ask;
  private double bid;
  private String currency;
  private String currencyProfit;
  private String description;
  private int instantMaxVolume;
  private double high;
  private double low;
  private String symbol;
  private Instant time;
  private int type;
  private String groupName;
  private String categoryName;
  private boolean longOnly;
  private Instant starting;
  private Instant expiration;
  private int stepRuleId;
  private int stopsLevel;
  private double lotMax;
  private double lotMin;
  private double lotStep;
  private int precision;
  private Long contractSize;
  private Long initialMargin;
  private double marginHedged;
  private boolean marginHedgedStrong;
  private Long marginMaintenance;
  private MarginMode marginMode;
  private double percentage;
  private ProfitMode profitMode;
  private double spreadRaw;
  private double spreadTable;
  private boolean swapEnable;
  private boolean shortSelling;
  private double swapLong;
  private double swapShort;
  private SwapType swapType;
  private SwapRolloverType swapRollover;
  private double tickSize;
  private double tickValue;
  private int quoteId;
  private String timeString;
  private double leverage;
  private boolean currencyPair;
  private boolean trailingEnabled;


  @Override
  public void setFieldsFromJSONObject(final JSONObject e) {
    if (e != null) {
      this.setAsk(e.getDouble("ask"));
      this.setBid(e.getDouble("bid"));
      this.setCategoryName(e.getString("categoryName"));
      this.setCurrency(e.getString("currency"));
      this.setDescription(e.getString("description"));
      final var expirationMillis = e.optLongObject("expiration", null);
      this.setExpiration(expirationMillis == null ? null : Instant.ofEpochMilli(expirationMillis));
      this.setGroupName(e.getString("groupName"));
      this.setHigh(e.getDouble("high"));
      this.setInstantMaxVolume(e.getInt("instantMaxVolume"));
      this.setLongOnly(e.getBoolean("longOnly"));
      this.setLotMax(e.getDouble("lotMax"));
      this.setLotMin(e.getDouble("lotMin"));
      this.setLow(e.getDouble("low"));
      this.setPrecision(e.getInt("precision"));
      this.setStepRuleId(e.getInt("stepRuleId"));
      final var startingMillis = e.optLongObject("starting", null);
      this.setStarting(startingMillis == null ? null : Instant.ofEpochMilli(startingMillis));
      this.setStopsLevel(e.getInt("stopsLevel"));
      this.setSymbol(e.getString("symbol"));
      this.setQuoteId(e.getInt("quoteId"));
      this.setCurrencyProfit(e.getString("currencyProfit"));
      final var timeMillis = e.getLong("time");
      this.setTime(Instant.ofEpochMilli(timeMillis));
      this.setTimeString(e.getString("timeString"));
      this.setType(e.getInt("type"));

      if (e.has("contractSize")) {
        this.setContractSize(e.getLong("contractSize"));
      }
      if (e.has("initialMargin")) {
        this.setInitialMargin(e.getLong("initialMargin"));
      }
      this.setLotStep(e.getDouble("lotStep"));
      if (e.has("marginHedged")) {
        this.setMarginHedged(e.getDouble("marginHedged"));
      }
      this.setMarginHedgedStrong((Boolean) e.get("marginHedgedStrong"));
      if (e.has("marginMaintenance")) {
        this.setMarginMaintenance(e.getLong("marginMaintenance"));
      }
      this.setMarginMode(new MarginMode(e.getInt("marginMode")));
      this.setPercentage(e.getDouble("percentage"));
      this.setProfitMode(new ProfitMode(e.getInt("profitMode")));
      this.setShortSelling(e.getBoolean("shortSelling"));
      this.setSwapEnable(e.getBoolean("swapEnable"));
      this.setSwapLong(e.getDouble("swapLong"));
      this.setSwapShort(e.getDouble("swapShort"));
      this.setSwapType(new SwapType(e.getInt("swapType")));
      this.setSwapRollover(new SwapRolloverType(e.getInt("swap_rollover3days")));
      this.setTickSize(e.getDouble("tickSize"));
      this.setTickValue(e.getDouble("tickValue"));
      this.currencyPair = e.getBoolean("currencyPair");
      this.leverage = e.getDouble("leverage");
      this.spreadRaw = e.getDouble("spreadRaw");
      this.spreadTable = e.getDouble("spreadTable");
      this.trailingEnabled = e.getBoolean("trailingEnabled");
    }
  }
}
