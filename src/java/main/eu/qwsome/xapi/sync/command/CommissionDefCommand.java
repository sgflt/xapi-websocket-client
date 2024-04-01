package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class CommissionDefCommand extends BaseCommand {

  public CommissionDefCommand(final String symbol, final Double volume) {
    super(
        new JSONObject()
            .put("symbol", symbol)
            .put("volume", volume)
    );
  }


  @Override
  public String toJSONString() {
    final var obj = new JSONObject();
    obj.put("command", this.commandName);
    obj.put("arguments", this.arguments);
    return obj.toString();
  }


  @Override
  public String getCommandName() {
    return "getCommissionDef";
  }

}
