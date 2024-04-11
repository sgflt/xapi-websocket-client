package eu.qwsome.xapi.stream.subscription;


import lombok.ToString;
import lombok.experimental.SuperBuilder;

@ToString
@SuperBuilder
public class StreamPingCommand extends StreamingCommandRecord {

  private final String streamSessionId;


  @Override
  public String getCommand() {
    return "ping";
  }


  @Override
  public String getStreamSessionId() {
    return this.streamSessionId;
  }
}
