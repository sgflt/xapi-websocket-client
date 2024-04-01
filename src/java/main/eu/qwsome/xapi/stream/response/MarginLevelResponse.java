package eu.qwsome.xapi.stream.response;


import org.json.JSONObject;

public class MarginLevelResponse extends BaseResponse {

  private final double balance;
  private final double equity;
  private final double margin;
  private final double margin_free;
  private final double margin_level;
  private final String currency;
  private final double credit;


  public MarginLevelResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    this.balance = (Double) ob.get("balance");
    this.credit = (Double) ob.get("credit");
    this.equity = (Double) ob.get("equity");
    this.currency = ob.getString("currency");
    this.margin = (Double) ob.get("margin");
    this.margin_free = (Double) ob.get("margin_free");
    this.margin_level = (Double) ob.get("margin_level");
  }


  public double getBalance() {
    return this.balance;
  }


  public double getEquity() {
    return this.equity;
  }


  public double getMargin() {
    return this.margin;
  }


  public double getMargin_free() {
    return this.margin_free;
  }


  public double getMargin_level() {
    return this.margin_level;
  }


  public String getCurrency() {
    return this.currency;
  }


  public double getCredit() {
    return this.credit;
  }


  @Override
  public String toString() {
    return "MarginLevelResponse [balance=" + this.balance + ", equity=" + this.equity
           + ", margin=" + this.margin + ", margin_free=" + this.margin_free
           + ", margin_level=" + this.margin_level + ", currency=" + this.currency
           + ", credit=" + this.credit + "]";
  }
}
