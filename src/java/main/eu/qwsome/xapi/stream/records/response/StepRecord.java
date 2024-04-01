package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class StepRecord implements BaseResponseRecord {

  private double fromValue;
  private double step;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.fromValue = ob.getDouble("fromValue");
    this.step = ob.getDouble("step");
  }
}
