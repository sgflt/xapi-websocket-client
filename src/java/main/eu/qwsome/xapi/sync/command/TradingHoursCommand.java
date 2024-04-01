package eu.qwsome.xapi.sync.command;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TradingHoursCommand extends BaseCommand {

  public TradingHoursCommand(final List<String> symbols) {
    super(
        new JSONObject().put("symbols", new JSONArray().putAll(symbols))
    );
  }


  @Override
  public String getCommandName() {
    return "getTradingHours";
  }
}
