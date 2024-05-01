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


import org.json.JSONObject;

public class MarginLevelResponse extends BaseResponse {

  private final double balance;
  private final double equity;
  private final double margin;
  private final double margin_free;
  private final double margin_level;
  private final String currency;
  private final double credit;


  public MarginLevelResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    this.balance = (Double) ob.get("balance");
    this.credit = (Double) ob.get("credit");
    this.equity = (Double) ob.get("equity");
    this.currency = ob.getString("currency");
    this.margin = (Double) ob.get("margin");
    this.margin_free = (Double) ob.get("margin_free");
    this.margin_level = (Double) ob.get("margin_level");
  }


  public double getBalance() {
    return this.balance;
  }


  public double getEquity() {
    return this.equity;
  }


  public double getMargin() {
    return this.margin;
  }


  public double getMargin_free() {
    return this.margin_free;
  }


  public double getMargin_level() {
    return this.margin_level;
  }


  public String getCurrency() {
    return this.currency;
  }


  public double getCredit() {
    return this.credit;
  }


  @Override
  public String toString() {
    return "MarginLevelResponse [balance=" + this.balance + ", equity=" + this.equity
           + ", margin=" + this.margin + ", margin_free=" + this.margin_free
           + ", margin_level=" + this.margin_level + ", currency=" + this.currency
           + ", credit=" + this.credit + "]";
  }
}
