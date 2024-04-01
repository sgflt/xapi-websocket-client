package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class SKeepAliveRecord implements BaseResponseRecord {

  private long timestamp;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.timestamp = ob.getLong("timestamp");
  }
}
