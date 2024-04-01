package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class MarginTradeCommand extends BaseCommand {

  public MarginTradeCommand(final String symbol, final double volume) {
    super(
        new JSONObject()
            .put("symbol", symbol)
            .put("volume", volume)
    );
  }


  @Override
  public String getCommandName() {
    return "getMarginTrade";
  }
}
