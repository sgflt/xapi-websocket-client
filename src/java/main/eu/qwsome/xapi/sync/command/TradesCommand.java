package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class TradesCommand extends BaseCommand {

  public TradesCommand(final boolean openedOnly) {
    super(
        new JSONObject().put("openedOnly", openedOnly)
    );
  }


  @Override
  public String getCommandName() {
    return "getTrades";
  }
}
