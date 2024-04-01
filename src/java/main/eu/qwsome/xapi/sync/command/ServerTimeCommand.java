package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class ServerTimeCommand extends BaseCommand {

  public ServerTimeCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getServerTime";
  }
}
