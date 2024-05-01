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

import eu.qwsome.xapi.stream.codes.StreamingTradeType;

@Getter
@ToString
public class STradeRecord extends WrapperTradeRecord {

  private StreamingTradeType type;
  private String state;

  private long open_time;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    super.setFieldsFromJSONObject(ob);
    this.type = new StreamingTradeType(ob.getLong("type"));
    this.state = ob.getString("state");

    this.open_time = ob.getLong("open_time");
    super.close_time = ob.optLong("close_time");

    super.expiration = ob.optLong("expiration");

    super.position = ob.getLong("position");

    super.profit = ob.optDouble("profit");

    super.sl = ob.getDouble("sl");
    super.tp = ob.getDouble("tp");
  }
}
