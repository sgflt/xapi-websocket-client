package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class ProxyVersionCommand extends BaseCommand {

  public ProxyVersionCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getDateOfBuild";
  }
}
