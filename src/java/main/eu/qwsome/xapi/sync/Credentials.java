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
import org.json.JSONObject;

@Getter
public class Credentials {

  private final String login;
  private final String password;
  private String appId;
  private String appName;


  public Credentials(final String login, final String password) {
    this.login = login;
    this.password = password;
  }


  public Credentials(final String login, final String password, final String appName) {
    this(login, password);
    this.appName = appName;
  }


  public Credentials(final String login, final String password, final String appId, final String appName) {
    this(login, password, appName);
    this.appId = appId;
  }


  public JSONObject toJson() {
    final var response = new JSONObject();
    response.put("userId", getLogin());
    response.put("password", getPassword());
    response.put("type", "java");
    response.put("version", "eu.qwsome.xtb-api");

    if (this.appId != null) {
      response.put("appId", this.appId);
    }

    if (this.appName != null) {
      response.put("appName", this.appName);
    }
    return response;
  }
}
