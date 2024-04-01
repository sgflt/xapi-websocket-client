package eu.qwsome.xapi.stream.codes;

public class TradeOperationCode extends ResponseCode {

  public static final TradeOperationCode BUY = new TradeOperationCode(0L);
  public static final TradeOperationCode SELL = new TradeOperationCode(1L);
  public static final TradeOperationCode BUY_LIMIT = new TradeOperationCode(2L);
  public static final TradeOperationCode SELL_LIMIT = new TradeOperationCode(3L);
  public static final TradeOperationCode BUY_STOP = new TradeOperationCode(4L);
  public static final TradeOperationCode SELL_STOP = new TradeOperationCode(5L);
  public static final TradeOperationCode BALANCE = new TradeOperationCode(6L);
  public static final TradeOperationCode CREDIT = new TradeOperationCode(7L);


  public TradeOperationCode(final long code) {
    super(code);
  }
}
