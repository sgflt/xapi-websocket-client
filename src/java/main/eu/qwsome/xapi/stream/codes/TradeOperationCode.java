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

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class TradeOperationCode extends ResponseCode {

  public static final TradeOperationCode BUY = new TradeOperationCode(0L);
  public static final TradeOperationCode SELL = new TradeOperationCode(1L);
  public static final TradeOperationCode BUY_LIMIT = new TradeOperationCode(2L);
  public static final TradeOperationCode SELL_LIMIT = new TradeOperationCode(3L);
  public static final TradeOperationCode BUY_STOP = new TradeOperationCode(4L);
  public static final TradeOperationCode SELL_STOP = new TradeOperationCode(5L);
  public static final TradeOperationCode BALANCE = new TradeOperationCode(6L);
  public static final TradeOperationCode CREDIT = new TradeOperationCode(7L);


  public TradeOperationCode(final long code) {
    super(code);
  }


  public static TradeOperationCode of(int code) {
    return new TradeOperationCode(code);
  }
}
