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
package eu.qwsome.xapi.sync.profit;


import org.json.JSONObject;

import eu.qwsome.xapi.codes.TradeOperationCode;
import eu.qwsome.xapi.sync.SynchronousCommand;

public class ProfitCalculationCommand extends SynchronousCommand {

  public ProfitCalculationCommand(
      final String symbol,
      final Double volume,
      final TradeOperationCode cmd,
      final Double openPrice,
      final Double closePrice
  ) {
    super(
        new JSONObject()
            .put("symbol", symbol)
            .put("volume", volume)
            .put("cmd", cmd.getCode())
            .put("openPrice", openPrice)
            .put("closePrice", closePrice)
    );
  }


  @Override
  public String getCommandName() {
    return "getProfitCalculation";
  }
}
