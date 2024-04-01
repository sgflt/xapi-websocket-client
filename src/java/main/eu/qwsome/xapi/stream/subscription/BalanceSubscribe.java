package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class BalanceSubscribe extends RecordSubscribe {

  @Override
  public String getCommand() {
    return "getBalance";
  }
}
