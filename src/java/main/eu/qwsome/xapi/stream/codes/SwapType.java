package eu.qwsome.xapi.stream.codes;

public class SwapType extends ResponseCode {

  public static final SwapType SWAP_BY_POINTS = new SwapType(0L);
  public static final SwapType SWAP_BY_DOLLARS = new SwapType(1L);
  public static final SwapType SWAP_BY_INTEREST = new SwapType(2L);
  public static final SwapType SWAP_BY_MARGIN_CURRENCY = new SwapType(3L);


  public SwapType(final long code) {
    super(code);
  }
}
