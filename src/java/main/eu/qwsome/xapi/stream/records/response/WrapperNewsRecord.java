package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
abstract class WrapperNewsRecord implements BaseResponseRecord {

  protected String body; //may not be present
  protected String title;
  protected String key;
  protected long time;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.key = ob.getString("key");
    this.time = ob.getLong("time");
    this.title = ob.getString("title");
    this.body = ob.getString("body");
  }
}
