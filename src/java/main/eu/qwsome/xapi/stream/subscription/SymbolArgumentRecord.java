package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;


@ToString
@SuperBuilder
public abstract class SymbolArgumentRecord extends StreamingCommandRecord {

  private final String symbol;


  @Override
  protected String getExtraKey() {
    return "symbol";
  }


  @Override
  protected String getExtraValue() {
    return this.symbol;
  }


  @Override
  protected abstract String getCommand();
}
