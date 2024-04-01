package eu.qwsome.xapi.stream.records.response;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.StreamingTradeType;

@Getter
@ToString
public class STradeRecord extends WrapperTradeRecord {

  private StreamingTradeType type;
  private String state;

  private long open_time;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    super.setFieldsFromJSONObject(ob);
    this.type = new StreamingTradeType(ob.getLong("type"));
    this.state = ob.getString("state");

    this.open_time = ob.getLong("open_time");
    super.close_time = ob.optLong("close_time");

    super.expiration = ob.optLong("expiration");

    super.position = ob.getLong("position");

    super.profit = ob.optDouble("profit");

    super.sl = ob.getDouble("sl");
    super.tp = ob.getDouble("tp");
  }
}
