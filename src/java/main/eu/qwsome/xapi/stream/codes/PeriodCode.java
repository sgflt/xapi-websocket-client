package eu.qwsome.xapi.stream.codes;

public final class PeriodCode extends ResponseCode {

  public static final PeriodCode PERIOD_M1 = new PeriodCode(1L);
  public static final PeriodCode PERIOD_M5 = new PeriodCode(5L);
  public static final PeriodCode PERIOD_M15 = new PeriodCode(15L);
  public static final PeriodCode PERIOD_M30 = new PeriodCode(30L);
  public static final PeriodCode PERIOD_H1 = new PeriodCode(60L);
  public static final PeriodCode PERIOD_H4 = new PeriodCode(240L);
  public static final PeriodCode PERIOD_D1 = new PeriodCode(1440L);
  public static final PeriodCode PERIOD_W1 = new PeriodCode(10080L);
  public static final PeriodCode PERIOD_MN1 = new PeriodCode(43200L);


  private PeriodCode(final long code) {
    super(code);
  }
}
