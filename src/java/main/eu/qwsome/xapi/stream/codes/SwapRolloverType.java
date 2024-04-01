package eu.qwsome.xapi.stream.codes;

public class SwapRolloverType extends ResponseCode {

  public static final SwapRolloverType MONDAY = new SwapRolloverType(0L);
  public static final SwapRolloverType TUESDAY = new SwapRolloverType(1L);
  public static final SwapRolloverType WEDNSDAY = new SwapRolloverType(2L);
  public static final SwapRolloverType THURSDAY = new SwapRolloverType(3L);
  public static final SwapRolloverType FRIDAY = new SwapRolloverType(4L);


  public SwapRolloverType(final long code) {
    super(code);
  }
}
