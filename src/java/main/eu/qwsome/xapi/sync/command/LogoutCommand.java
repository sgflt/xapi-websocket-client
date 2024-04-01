package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class LogoutCommand extends BaseCommand {

  public LogoutCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "logout";
  }
}
