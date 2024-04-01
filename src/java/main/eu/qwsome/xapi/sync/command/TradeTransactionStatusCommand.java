package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class TradeTransactionStatusCommand extends BaseCommand {

  public TradeTransactionStatusCommand(final long order) {
    super(
        new JSONObject().put("order", order)
    );
  }


  @Override
  public String getCommandName() {
    return "tradeTransactionStatus";
  }
}
