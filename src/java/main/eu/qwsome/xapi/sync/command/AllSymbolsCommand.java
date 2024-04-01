package eu.qwsome.xapi.sync.command;

import org.json.JSONObject;

public class AllSymbolsCommand extends BaseCommand {

  public AllSymbolsCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getAllSymbols";
  }

}
