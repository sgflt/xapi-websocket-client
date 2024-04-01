package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.TradeOperationCode;

public class ProfitCalculationCommand extends BaseCommand {

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
