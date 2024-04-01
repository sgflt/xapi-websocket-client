package eu.qwsome.xapi.sync.command;

import eu.qwsome.xapi.stream.records.request.ChartLastInfoRecord;

public class ChartLastCommand extends BaseCommand {

  public ChartLastCommand(final ChartLastInfoRecord info) {
    super(info.toJSONObject());
  }


  @Override
  public String getCommandName() {
    return "getChartLastRequest";
  }
}
