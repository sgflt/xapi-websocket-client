package eu.qwsome.xapi.sync.command;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TickPricesCommand extends BaseCommand {

  public TickPricesCommand(
      final Long level,
      final List<String> symbols,
      final Long timestamp
  ) {
    super(
        new JSONObject()
            .put("symbols", new JSONArray().putAll(symbols))
            .put("timestamp", timestamp)
            .put("level", level)
    );
  }


  @Override
  public String getCommandName() {
    return "getTickPrices";
  }
}
