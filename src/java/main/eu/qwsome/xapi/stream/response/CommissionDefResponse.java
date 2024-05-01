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

public class CommissionDefResponse extends BaseResponse {

  private double commission;
  private double rateOfExchange;


  public CommissionDefResponse(final String body) {
    super(body);
    final var rd = (JSONObject) this.getReturnData();
    if (rd != null) {
      this.commission = (Double) rd.get("commission");
      this.rateOfExchange = (Double) rd.get("rateOfExchange");
    }
  }


  public double getCommission() {
    return this.commission;
  }


  public double getRateOfExchange() {
    return this.rateOfExchange;
  }


  @Override
  public String toString() {
    return "CommissionDefResponse [commission=" + this.commission
           + ", rateOfExchange=" + this.rateOfExchange + "]";
  }
}
