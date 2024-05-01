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


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.MarginMode;
import eu.qwsome.xapi.stream.codes.ProfitMode;
import eu.qwsome.xapi.stream.codes.SwapRolloverType;
import eu.qwsome.xapi.stream.codes.SwapType;

@Getter
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
  private long time;
  private int type;
  private String groupName;
  private String categoryName;
  private boolean longOnly;
  private Long starting;
  private Long expiration;
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


  @Override
  public void setFieldsFromJSONObject(final JSONObject e) {
    if (e != null) {
      this.setAsk(e.getDouble("ask"));
      this.setBid(e.getDouble("bid"));
      this.setCategoryName(e.getString("categoryName"));
      this.setCurrency(e.getString("currency"));
      this.setDescription(e.getString("description"));
      this.setExpiration(e.optLongObject("expiration", null));
      this.setGroupName(e.getString("groupName"));
      this.setHigh(e.getDouble("high"));
      this.setInstantMaxVolume(e.getInt("instantMaxVolume"));
      this.setLongOnly(e.getBoolean("longOnly"));
      this.setLotMax(e.getDouble("lotMax"));
      this.setLotMin(e.getDouble("lotMin"));
      this.setLow(e.getDouble("low"));
      this.setPrecision(e.getInt("precision"));
      this.setStepRuleId(e.getInt("stepRuleId"));
      this.setStarting(e.optLongObject("starting", null));
      this.setStopsLevel(e.getInt("stopsLevel"));
      this.setSymbol(e.getString("symbol"));
      this.setQuoteId(e.getInt("quoteId"));
      this.setCurrencyProfit(e.getString("currencyProfit"));
      this.setTime(e.getLong("time"));
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
        this.setMarginHedged(Double.valueOf(e.get("marginHedged").toString()));
      }
      this.setMarginHedgedStrong((Boolean) e.get("marginHedgedStrong"));
      if (e.has("marginMaintenance")) {
        this.setMarginMaintenance(e.getLong("marginMaintenance"));
      }
      this.setMarginMode(new MarginMode(e.getInt("marginMode")));
      this.setPercentage(e.getDouble("percentage"));
      this.setProfitMode(new ProfitMode(e.getInt("profitMode")));
      this.setSwapEnable((Boolean) e.get("swapEnable"));
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
    }
  }


  public void setTimeString(final String timeString) {
    this.timeString = timeString;
  }


  public void setCurrencyProfit(final String currencyProfit) {
    this.currencyProfit = currencyProfit;
  }


  public void setQuoteId(final int quoteId) {
    this.quoteId = quoteId;
  }


  public void setAsk(final Double ask) {
    this.ask = ask;
  }


  public void setBid(final Double bid) {
    this.bid = bid;
  }


  public void setCurrency(final String currency) {
    this.currency = currency;
  }


  public void setDescription(final String description) {
    this.description = description;
  }


  public void setInstantMaxVolume(final int instantMaxVolume) {
    this.instantMaxVolume = instantMaxVolume;
  }


  public void setHigh(final Double high) {
    this.high = high;
  }


  public void setLow(final Double low) {
    this.low = low;
  }


  public String getSymbol() {
    return this.symbol;
  }


  public void setSymbol(final String symbol) {
    this.symbol = symbol;
  }


  public void setTime(final Long time) {
    this.time = time;
  }


  public void setType(final int type) {
    this.type = type;
  }


  public void setGroupName(final String groupName) {
    this.groupName = groupName;
  }


  public void setCategoryName(final String categoryName) {
    this.categoryName = categoryName;
  }


  public void setLongOnly(final boolean longOnly) {
    this.longOnly = longOnly;
  }


  public void setStarting(final Long starting) {
    this.starting = starting;
  }


  public void setExpiration(final Long expiration) {
    this.expiration = expiration;
  }


  public void setStopsLevel(final int stopsLevel) {
    this.stopsLevel = stopsLevel;
  }


  public void setLotMax(final Double lotMax) {
    this.lotMax = lotMax;
  }


  public void setLotMin(final Double lotMin) {
    this.lotMin = lotMin;
  }


  public void setPrecision(final int precision) {
    this.precision = precision;
  }


  public void setContractSize(final Long contractSize) {
    this.contractSize = contractSize;
  }


  public void setInitialMargin(final Long initialMargin) {
    this.initialMargin = initialMargin;
  }


  public void setLotStep(final Double lotStep) {
    this.lotStep = lotStep;
  }


  public void setMarginHedged(final Double marginHedged) {
    this.marginHedged = marginHedged;
  }


  public void setMarginHedgedStrong(final Boolean marginHedgedStrong) {
    this.marginHedgedStrong = marginHedgedStrong;
  }


  public void setMarginMaintenance(final Long marginMaintenance) {
    this.marginMaintenance = marginMaintenance;
  }


  public void setMarginMode(final MarginMode marginMode) {
    this.marginMode = marginMode;
  }


  public void setPercentage(final Double percentage) {
    this.percentage = percentage;
  }


  public void setProfitMode(final ProfitMode profitMode) {
    this.profitMode = profitMode;
  }


  public void setSwapEnable(final boolean swapEnable) {
    this.swapEnable = swapEnable;
  }


  public void setSwapLong(final Double swapLong) {
    this.swapLong = swapLong;
  }


  public void setSwapShort(final Double swapShort) {
    this.swapShort = swapShort;
  }


  public void setSwapType(final SwapType swapType) {
    this.swapType = swapType;
  }


  public void setSwapRollover(final SwapRolloverType swap_rollover) {
    this.swapRollover = swap_rollover;
  }


  public void setTickSize(final double tickSize) {
    this.tickSize = tickSize;
  }


  public void setTickValue(final double tickValue) {
    this.tickValue = tickValue;
  }


  public void setStepRuleId(final int stepRuleId) {
    this.stepRuleId = stepRuleId;
  }
}
