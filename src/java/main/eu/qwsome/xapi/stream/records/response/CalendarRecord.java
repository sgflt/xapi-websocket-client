package eu.qwsome.xapi.stream.records.response;


import lombok.Data;
import org.json.JSONObject;

@Data
public class CalendarRecord implements BaseResponseRecord {

  private String country;
  private String current;
  private String forecast;
  private String impact;
  private String period;
  private String previous;
  private long time;
  private String title;


  @Override
  public void setFieldsFromJSONObject(final JSONObject e) {
    this.setCountry(e.getString("country"));
    this.setCurrent(e.getString("current"));
    this.setForecast(e.getString("forecast"));
    this.setImpact(e.getString("impact"));
    this.setPeriod(e.getString("period"));
    this.setPrevious(e.getString("previous"));
    this.setTitle(e.getString("title"));
    this.setTime(e.getLong("time"));
  }
}
