package eu.qwsome.xapi.stream.codes;

public final class MarginMode extends ResponseCode {

  public static final MarginMode FOREX = new MarginMode(101L);
  public static final MarginMode CFD_LEVERAGED = new MarginMode(102L);
  public static final MarginMode CFD = new MarginMode(103L);


  public MarginMode(final long code) {
    super(code);
  }
}
