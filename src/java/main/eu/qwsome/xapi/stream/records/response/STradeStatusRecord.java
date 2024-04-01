package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.RequestStatus;

@Getter
@ToString
public class STradeStatusRecord implements BaseResponseRecord {

  private int order;
  private RequestStatus requestStatus;
  private String message;
  private String customComment;
  private Double price;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.order = ob.getInt("order");
    this.price = ob.getDouble("price");
    this.requestStatus = new RequestStatus(ob.getLong("requestStatus"));
    this.message = ob.optString("message");
    this.customComment = ob.optString("customComment");
  }
}
