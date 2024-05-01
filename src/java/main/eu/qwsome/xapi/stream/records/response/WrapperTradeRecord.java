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

@Getter
@ToString
abstract class WrapperTradeRecord implements BaseResponseRecord {

  private double close_price;
  private boolean closed;
  private int cmd;
  private String comment;
  private String customComment;
  private double commission;
  private long order;
  private long order2;
  private double volume;
  private double margin_rate;
  private double open_price;
  private String symbol;
  private double storage;
  private int digits;

  protected Long close_time;
  protected Long expiration;
  protected long position;
  protected Double profit;
  protected double sl;
  protected double tp;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.close_price = ob.getDouble("close_price");
    this.closed = ob.getBoolean("closed");
    this.cmd = ob.getInt("cmd");
    this.comment = ob.getString("comment");
    this.customComment = ob.optString("customComment");
    this.commission = ob.getDouble("commission");
    this.order = ob.getLong("order");
    this.order2 = ob.getLong("order2");
    this.volume = ob.getDouble("volume");
    this.margin_rate = ob.getDouble("margin_rate");
    this.open_price = ob.getDouble("open_price");
    this.symbol = ob.getString("symbol");
    this.storage = ob.getDouble("storage");
    this.digits = ob.getInt("digits");
  }
}
