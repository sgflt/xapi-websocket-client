package eu.qwsome.xapi.stream.codes;

public class StreamingTradeType extends ResponseCode {

  public static final StreamingTradeType OPEN = new StreamingTradeType(0L);
  public static final StreamingTradeType PENDING = new StreamingTradeType(1L);
  public static final StreamingTradeType CLOSE = new StreamingTradeType(2L);


  public StreamingTradeType(final long code) {
    super(code);
  }
}
