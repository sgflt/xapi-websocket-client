package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class CurrentUserDataCommand extends BaseCommand {

  public CurrentUserDataCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getCurrentUserData";
  }
}
