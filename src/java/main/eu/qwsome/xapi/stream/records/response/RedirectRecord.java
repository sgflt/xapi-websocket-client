package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class RedirectRecord implements BaseResponseRecord {

  private int mainPort;
  private int streamingPort;
  private String address;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.mainPort = ob.getInt("mainPort");
    this.streamingPort = ob.getInt("streamingPort");
    this.address = ob.getString("address");
  }
}
