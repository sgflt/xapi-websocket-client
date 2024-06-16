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
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
abstract class WrapperTradeRecord implements BaseResponseRecord {

  private double closePrice;
  private boolean closed;
  private int cmd;
  private String comment;
  private String customComment;
  private double commission;
  private long order;
  private long order2;
  private double volume;
  private double marginRate;
  private double openPrice;
  private String symbol;
  private double storage;
  private int digits;

  protected Instant closeTime;
  protected Instant expiration;
  protected long position;
  protected Double profit;
  protected double sl;
  protected double tp;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.closePrice = ob.getDouble("close_price");
    this.closed = ob.getBoolean("closed");
    this.cmd = ob.getInt("cmd");
    this.comment = ob.optString("comment");
    this.customComment = ob.optString("customComment");
    this.commission = ob.getDouble("commission");
    this.order = ob.getLong("order");
    this.order2 = ob.getLong("order2");
    this.volume = ob.getDouble("volume");
    this.marginRate = ob.getDouble("margin_rate");
    this.openPrice = ob.getDouble("open_price");
    this.symbol = ob.getString("symbol");
    this.storage = ob.getDouble("storage");
    this.digits = ob.getInt("digits");
  }
}
