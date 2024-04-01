package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class TradeTransactionResponse extends BaseResponse {

  private long order;


  public TradeTransactionResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    if (getStatus()) {
      this.order = ob.getLong("order");
    }
  }
}
