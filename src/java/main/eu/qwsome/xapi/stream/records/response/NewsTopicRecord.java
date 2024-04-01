package eu.qwsome.xapi.stream.records.response;


import org.json.JSONObject;

public class NewsTopicRecord extends WrapperNewsRecord {

  private String timeString;
  private int bodylen;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    super.setFieldsFromJSONObject(ob);
    this.timeString = ob.getString("timeString");
    this.bodylen = ob.getInt("bodylen");
  }


  public String getTimeString() {
    return this.timeString;
  }


  public int getBodylen() {
    return this.bodylen;
  }


  @Override
  public String toString() {
    return "NewsTopicRecord [timeString=" + this.timeString + ", bodylen="
           + this.bodylen + ", toString()=" + super.toString() + "]";
  }
}
