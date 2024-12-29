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
package eu.qwsome.xapi.sync;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

import eu.qwsome.xapi.codes.ErrorCode;
import eu.qwsome.xapi.error.XtbApiException;

@Getter
@ToString
public abstract class SynchronousResponse {

  private final boolean status;
  private final String errorDescr;
  private final ErrorCode errCode;
  private final Object returnData;
  private JSONObject redirectJson;
  private final String streamSessionId;


  protected SynchronousResponse(final JSONObject ob) {
    try {
      this.status = ob.getBoolean("status");
      this.errCode = ob.has("errorCode") ? new ErrorCode(ob.getString("errorCode")) : null;
      this.errorDescr = ob.optString("errorDescr");

      if (!this.status) {
        throw new XtbApiException(String.format("%s | %s", this.errCode, this.errorDescr));
      }

      this.returnData = ob.has("returnData") ? ob.get("returnData") : null;
      this.streamSessionId = ob.optString("streamSessionId");

      parseRedirect(ob);
    } catch (final JSONException e) {
      throw new XtbApiException("JSON Parse exception: " + ob);
    }
  }


  private void parseRedirect(final JSONObject ob) {
    try {
      this.redirectJson = (JSONObject) ob.get("redirect");
    } catch (final Exception ignore) {
      this.redirectJson = null;
    }
  }
}
