package eu.qwsome.xapi.stream.response;


import lombok.Data;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.BaseResponseRecord;

@Data
public class HoursRecord implements BaseResponseRecord {

  private long day;
  private long fromT;
  private long toT;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.day = ob.getLong("day");
    this.fromT = ob.getLong("fromT");
    this.toT = ob.getLong("toT");
  }
}
