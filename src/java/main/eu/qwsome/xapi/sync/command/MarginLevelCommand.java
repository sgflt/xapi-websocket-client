package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class MarginLevelCommand extends BaseCommand {

  public MarginLevelCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getMarginLevel";
  }


  @Override
  public Long getTimeoutMillis() {
    return 200L;
  }
}
