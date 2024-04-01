package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class SProfitRecord implements BaseResponseRecord {

  private long order;
  private long order2;
  private long position;
  private double profit;


  @Override
  public void setFieldsFromJSONObject(final JSONObject e) {
    this.order = e.getLong("order");
    this.order2 = e.getLong("order2");
    this.position = e.getLong("position");
    this.profit = e.getLong("profit");
  }
}
