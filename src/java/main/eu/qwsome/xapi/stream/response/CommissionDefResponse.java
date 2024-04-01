package eu.qwsome.xapi.stream.response;


import org.json.JSONObject;

public class CommissionDefResponse extends BaseResponse {

  private double commission;
  private double rateOfExchange;


  public CommissionDefResponse(final String body) {
    super(body);
    final var rd = (JSONObject) this.getReturnData();
    if (rd != null) {
      this.commission = (Double) rd.get("commission");
      this.rateOfExchange = (Double) rd.get("rateOfExchange");
    }
  }


  public double getCommission() {
    return this.commission;
  }


  public double getRateOfExchange() {
    return this.rateOfExchange;
  }


  @Override
  public String toString() {
    return "CommissionDefResponse [commission=" + this.commission
           + ", rateOfExchange=" + this.rateOfExchange + "]";
  }
}
