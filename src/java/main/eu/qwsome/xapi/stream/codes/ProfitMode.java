package eu.qwsome.xapi.stream.codes;

public class ProfitMode extends ResponseCode {

  public static final ProfitMode FOREX = new ProfitMode(5L);
  public static final ProfitMode CFD = new ProfitMode(6L);


  public ProfitMode(final long code) {
    super(code);
  }
}
