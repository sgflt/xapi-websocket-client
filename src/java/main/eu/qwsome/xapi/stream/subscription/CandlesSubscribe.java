package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class CandlesSubscribe extends SymbolArgumentRecord {

  private final String streamSessionId;


  @Override
  public String getCommand() {
    return "getCandles";
  }


  @Override
  protected String getStreamSessionId() {
    return this.streamSessionId;
  }
}
