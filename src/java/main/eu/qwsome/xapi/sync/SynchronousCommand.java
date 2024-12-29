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

import java.util.Objects;

import lombok.ToString;
import org.json.JSONObject;

/**
 * Abstract base class for each Command.
 */
@ToString
public abstract class SynchronousCommand {

  protected JSONObject arguments;


  /**
   * Constructor for base command.
   *
   * @param arguments Command arguments (see xAPI Documentation)
   */
  protected SynchronousCommand(final JSONObject arguments) {
    this.arguments = Objects.requireNonNull(arguments);
  }


  /**
   * Returns command name
   *
   * @return command name
   */
  public abstract String getCommandName();


  /**
   * Creates a JSON String from this object
   *
   * @return
   */
  public String toJSONString() {
    final var obj = new JSONObject();
    obj.put("command", Objects.requireNonNull(getCommandName()));
    obj.put("prettyPrint", false);
    obj.put("arguments", this.arguments);
    return obj.toString();
  }
}
