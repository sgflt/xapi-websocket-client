package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class TickPricesSubscribe extends SymbolArgumentRecord {

  private final String streamSessionId;
  private final int minArrivalTime;
  private final int maxLevel;


  @Override
  protected String getExtraKey2() {
    return this.minArrivalTime == 0 ? "" : "minArrivalTime";
  }


  @Override
  protected int getExtraIntValue2() {
    return this.minArrivalTime;
  }


  @Override
  protected String getExtraKey3() {
    return this.maxLevel == -1 ? "" : "maxLevel";
  }


  @Override
  protected int getExtraIntValue3() {
    return this.maxLevel;
  }


  @Override
  public String getCommand() {
    return "getTickPrices";
  }


  @Override
  protected String getStreamSessionId() {
    return this.streamSessionId;
  }
}
