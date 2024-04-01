package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class PingCommand extends BaseCommand {

  public PingCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "ping";
  }
}
