package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class TradeRecordsSubscribe extends RecordSubscribe {

  @Override
  protected String getCommand() {
    return "getTrades";
  }
}
