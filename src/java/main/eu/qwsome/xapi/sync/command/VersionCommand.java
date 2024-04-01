package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class VersionCommand extends BaseCommand {

  public VersionCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getVersion";
  }
}
