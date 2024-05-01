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
package eu.qwsome.xapi.stream.subscription;


import lombok.experimental.SuperBuilder;
import org.json.JSONObject;

@SuperBuilder
public abstract class StreamingCommandRecord {

  public final String toJSONString() {
    final var obj = new JSONObject();
    obj.put("command", this.getCommand());

    final String extraKey = this.getExtraKey();
    if (!extraKey.isEmpty()) {
      obj.put(extraKey, getExtraValue());
    }

    final String extraKey2 = this.getExtraKey2();
    if (!extraKey2.isEmpty()) {
      obj.put(extraKey2, getExtraIntValue2());
    }

    final String extraKey3 = this.getExtraKey3();
    if (!extraKey3.isEmpty()) {
      obj.put(extraKey3, getExtraIntValue3());
    }

    final String streamSessionId = getStreamSessionId();
    if (!streamSessionId.isEmpty()) {
      obj.put("streamSessionId", streamSessionId);
    }
    return obj.toString();
  }


  protected String getExtraKey() {
    return "";
  }


  protected String getExtraValue() {
    return "";
  }


  protected String getExtraKey2() {
    return "";
  }


  protected int getExtraIntValue2() {
    return 0;
  }


  protected String getExtraKey3() {
    return "";
  }


  protected int getExtraIntValue3() {
    return 0;
  }


  protected String getStreamSessionId() {
    return "";
  }


  protected abstract String getCommand();
}
