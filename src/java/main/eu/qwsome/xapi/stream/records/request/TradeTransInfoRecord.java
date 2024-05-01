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
package eu.qwsome.xapi.stream.records.request;

import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.TradeOperationCode;
import eu.qwsome.xapi.stream.codes.TradeTransactionType;

@Data
@Builder
public class TradeTransInfoRecord {

  private final TradeOperationCode cmd;
  private final TradeTransactionType type;
  private final Double price;
  private final Double sl;
  private final Double tp;
  private final String symbol;
  private final Double volume;
  private final Long order;
  private final Long expiration;
  private final Double offset;
  private final String customComment;


  public JSONObject toJSONObject() {
    final var obj = new JSONObject();
    obj.put("cmd", this.cmd.getCode());
    obj.put("customComment", this.customComment);
    obj.put("expiration", this.expiration);
    obj.put("offset", this.offset);
    obj.put("order", this.order);
    obj.put("price", this.price);
    obj.put("sl", this.sl);
    obj.put("symbol", this.symbol);
    obj.put("tp", this.tp);
    obj.put("type", this.type.getCode());
    obj.put("volume", this.volume);

    return new JSONObject().put("tradeTransInfo", obj);
  }
}
