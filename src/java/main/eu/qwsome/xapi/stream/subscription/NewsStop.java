package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class NewsStop extends StreamingCommandRecord {

  @Override
  protected String getCommand() {
    return "stopNews";
  }
}