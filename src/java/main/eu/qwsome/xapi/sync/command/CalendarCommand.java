package eu.qwsome.xapi.sync.command;


import org.json.JSONObject;

public class CalendarCommand extends BaseCommand {

  public CalendarCommand() {
    super(new JSONObject());
  }


  @Override
  public String getCommandName() {
    return "getCalendar";
  }
}
