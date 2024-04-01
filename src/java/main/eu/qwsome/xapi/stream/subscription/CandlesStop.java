package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class CandlesStop extends SymbolArgumentRecord {

  @Override
  protected String getCommand() {
    return "stopCandles";
  }
}
