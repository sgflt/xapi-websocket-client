package eu.qwsome.xapi.stream.response;


import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.RequestStatus;

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
      this.ask = Double.valueOf(ob.get("ask").toString());
      this.bid = Double.valueOf(ob.get("bid").toString());
      this.order = ob.getLong("order");
      this.message = ob.getString("message");
      this.customComment = ob.getString("customComment");
      this.requestStatus = new RequestStatus(ob.getLong("requestStatus"));
    }
  }


  public Double getAsk() {
    return this.ask;
  }


  public String getMessage() {
    return this.message;
  }


  public Double getBid() {
    return this.bid;
  }


  public Long getOrder() {
    return this.order;
  }


  public RequestStatus getRequestStatus() {
    return this.requestStatus;
  }


  public String getCustomComment() {
    return this.customComment;
  }


  @Override
  public String toString() {
    return "TradeTransactionStatusResponse [ask=" + this.ask + ", bid=" + this.bid
           + ", message=" + this.message + ", order=" + this.order
           + ", requestStatus=" + this.requestStatus + ", customComment="
           + this.customComment + "]";
  }
}
