package eu.qwsome.xapi.sync.command;


import eu.qwsome.xapi.stream.records.request.ChartRangeInfoRecord;

public class ChartRangeCommand extends BaseCommand {

  public ChartRangeCommand(final ChartRangeInfoRecord info) {
    super(info.toJSONObject());
  }


  @Override
  public String getCommandName() {
    return "getChartRangeRequest";
  }
}
