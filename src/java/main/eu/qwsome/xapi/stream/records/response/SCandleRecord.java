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
public class SCandleRecord implements BaseResponseRecord {

  private long ctm;
  private String ctmString;
  private double open;
  private double high;
  private double low;
  private double close;
  private double vol;
  private int quoteId;
  private String symbol;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.ctm = ob.getLong("ctm");
    this.ctmString = ob.getString("ctmString");
    this.symbol = ob.getString("symbol");
    this.quoteId = ob.getInt("quoteId");
    this.open = ob.getDouble("open");
    this.high = ob.getDouble("high");
    this.low = ob.getDouble("low");
    this.close = ob.getDouble("close");
    this.vol = ob.getDouble("vol");
  }
}
