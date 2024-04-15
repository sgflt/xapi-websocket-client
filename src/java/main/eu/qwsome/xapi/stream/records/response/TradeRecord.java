package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class TradeRecord extends WrapperTradeRecord {

  private long timestamp;

  private Long open_time;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    super.setFieldsFromJSONObject(ob);
    this.timestamp = ob.getLong("timestamp");

    this.open_time = ob.optLongObject("open_time");

    super.close_time = ob.optLongObject("close_time", null);
    super.expiration = ob.optLongObject("expiration", null);
    super.position = ob.getLong("position");
    super.profit = ob.optDouble("profit");
    super.sl = ob.getDouble("sl");
    super.tp = ob.getDouble("tp");
  }
}
