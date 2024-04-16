package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.RequestStatus;

@Getter
@ToString
public class TradeTransactionStatusResponse extends BaseResponse {

  private Double ask;
  private Double bid;
  private String message;
  private Long order;
  private RequestStatus requestStatus;
  private String customComment;


  public TradeTransactionStatusResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    if (getStatus()) {
      this.ask = ob.getDouble("ask");
      this.bid = ob.getDouble("bid");
      this.order = ob.getLong("order");
      this.message = ob.optString("message");
      this.customComment = ob.optString("customComment");
      this.requestStatus = new RequestStatus(ob.getLong("requestStatus"));
    }
  }
}
