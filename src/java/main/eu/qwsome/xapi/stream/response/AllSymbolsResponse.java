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

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.SymbolRecord;

@Getter
@ToString
public class AllSymbolsResponse extends BaseResponse {

  private final List<SymbolRecord> symbolRecords;


  public AllSymbolsResponse(final String body) {
    super(body);
    final var symbols = (JSONArray) this.getReturnData();
    this.symbolRecords = new ArrayList<>(symbols.length());
    for (final var symbol : symbols) {
      final JSONObject e = (JSONObject) symbol;
      final var symbolRecord = new SymbolRecord();
      symbolRecord.setFieldsFromJSONObject(e);
      this.symbolRecords.add(symbolRecord);
    }
  }
}
