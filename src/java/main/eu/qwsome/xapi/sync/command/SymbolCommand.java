package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class SymbolCommand extends BaseCommand {

  public SymbolCommand(final String symbol) {
    super(new JSONObject().put("symbol", symbol));
  }


  @Override
  public String getCommandName() {
    return "getSymbol";
  }
}
