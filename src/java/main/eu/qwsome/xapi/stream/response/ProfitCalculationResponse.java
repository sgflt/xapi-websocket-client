package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class ProfitCalculationResponse extends BaseResponse {

  private Double profit;


  public ProfitCalculationResponse(final String body) {
    super(body);
    final var ob = (JSONObject) this.getReturnData();
    if (ob != null) {
      this.profit = (Double) ob.get("profit");
    }
  }
}
