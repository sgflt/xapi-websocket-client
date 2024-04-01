package eu.qwsome.xapi.sync.command;


import java.time.LocalDateTime;
import java.time.ZoneId;

import org.json.JSONObject;

public class NewsCommand extends BaseCommand {

  public NewsCommand(final LocalDateTime start, final LocalDateTime end) {
    super(
        new JSONObject()
            .put("start", start.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
            .put("end", end.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
    );
  }


  @Override
  public String getCommandName() {
    return "getNews";
  }
}
