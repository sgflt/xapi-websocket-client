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
package eu.qwsome.xapi.sync.chart;


import java.time.Instant;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class RateInfoRecord {

  private final Instant ctm;
  private final double open;
  private final double high;
  private final double low;
  private final double close;
  private final double vol;


  public RateInfoRecord(final JSONObject e) {
    this.close = e.getDouble("close");
    this.ctm = Instant.ofEpochMilli(e.getLong("ctm"));
    this.high = e.getDouble("high");
    this.low = e.getDouble("low");
    this.open = e.getDouble("open");
    this.vol = e.getDouble("vol");
  }
}
