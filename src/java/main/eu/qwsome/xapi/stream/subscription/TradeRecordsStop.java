package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class TradeRecordsStop extends StreamingCommandRecord {

  @Override
  protected String getCommand() {
    return "stopTrades";
  }
}
