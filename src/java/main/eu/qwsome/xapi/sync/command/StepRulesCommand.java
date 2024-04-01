package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class StepRulesCommand extends BaseCommand {

  public StepRulesCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getStepRules";
  }
}
