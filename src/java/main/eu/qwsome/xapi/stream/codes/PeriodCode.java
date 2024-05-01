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
package eu.qwsome.xapi.stream.codes;

public final class PeriodCode extends ResponseCode {

  public static final PeriodCode PERIOD_M1 = new PeriodCode(1L);
  public static final PeriodCode PERIOD_M5 = new PeriodCode(5L);
  public static final PeriodCode PERIOD_M15 = new PeriodCode(15L);
  public static final PeriodCode PERIOD_M30 = new PeriodCode(30L);
  public static final PeriodCode PERIOD_H1 = new PeriodCode(60L);
  public static final PeriodCode PERIOD_H4 = new PeriodCode(240L);
  public static final PeriodCode PERIOD_D1 = new PeriodCode(1440L);
  public static final PeriodCode PERIOD_W1 = new PeriodCode(10080L);
  public static final PeriodCode PERIOD_MN1 = new PeriodCode(43200L);


  private PeriodCode(final long code) {
    super(code);
  }
}
