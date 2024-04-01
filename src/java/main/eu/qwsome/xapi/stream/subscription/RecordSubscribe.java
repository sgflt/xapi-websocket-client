package eu.qwsome.xapi.stream.subscription;

import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public abstract class RecordSubscribe extends StreamingCommandRecord {

  private final String streamSessionId;


  @Override
  protected String getStreamSessionId() {
    return this.streamSessionId;
  }


  protected abstract String getCommand();
}
