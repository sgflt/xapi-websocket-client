package eu.qwsome.xapi.stream.codes;

public final class TradeTransactionType extends ResponseCode {

  public static final TradeTransactionType OPEN = new TradeTransactionType(0L);
  public static final TradeTransactionType CLOSE = new TradeTransactionType(2L);
  public static final TradeTransactionType MODIFY = new TradeTransactionType(3L);
  public static final TradeTransactionType DELETE = new TradeTransactionType(4L);


  private TradeTransactionType(final long code) {
    super(code);
  }
}
