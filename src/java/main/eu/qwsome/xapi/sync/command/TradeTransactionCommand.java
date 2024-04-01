package eu.qwsome.xapi.sync.command;


import eu.qwsome.xapi.stream.records.request.TradeTransInfoRecord;

public class TradeTransactionCommand extends BaseCommand {

  public TradeTransactionCommand(final TradeTransInfoRecord tradeTransInfo) {
    super(tradeTransInfo.toJSONObject());
  }


  @Override
  public String getCommandName() {
    return "tradeTransaction";
  }
}
