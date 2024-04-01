package eu.qwsome.xapi.sync.command;


import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class TradeRecordsCommand extends BaseCommand {

  public TradeRecordsCommand(final List<Long> orders) {
    super(
        new JSONObject().put("orders", new JSONArray().putAll(orders))
    );
  }


  @Override
  public String getCommandName() {
    return "getTradeRecords";
  }
}
