package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.RateInfoRecord;

@Getter
@ToString
public class ChartResponse extends BaseResponse {

  private final int digits;
  private final List<RateInfoRecord> rateInfos;


  public ChartResponse(final String body) {
    super(body);

    final var rd = (JSONObject) this.getReturnData();
    this.rateInfos = new ArrayList<>(rd.length());
    this.digits = ((Long) rd.get("digits")).intValue();
    final var arr = (JSONArray) rd.get("rateInfos");
    for (final Object o : arr) {
      final var e = (JSONObject) o;
      final var record = new RateInfoRecord();
      record.setFieldsFromJSONObject(e);
      this.rateInfos.add(record);
    }
  }
}
