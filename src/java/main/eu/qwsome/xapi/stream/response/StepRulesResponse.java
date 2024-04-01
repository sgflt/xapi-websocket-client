package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.StepRecord;

@Getter
@ToString
public class StepRulesResponse extends BaseResponse {

  private final List<Integer> ids;
  private final List<String> names;
  private final List<List<StepRecord>> stepRecords;


  public StepRulesResponse(final String body) {
    super(body);

    final var returnDataArray = (JSONArray) this.getReturnData();
    this.ids = new ArrayList<>(returnDataArray.length());
    this.names = new ArrayList<>(returnDataArray.length());
    this.stepRecords = new ArrayList<>(returnDataArray.length());

    for (final var o : returnDataArray) {
      final var ob = (JSONObject) o;
      final int id = ob.getInt("id");
      this.ids.add(id);

      final String name = ob.getString("name");
      this.names.add(name);

      final var stepsArray = (JSONArray) ob.get("steps");
      final var stepsList = new ArrayList<StepRecord>(stepsArray.length());
      for (final var object : stepsArray) {
        final var e = (JSONObject) object;
        final var rec = new StepRecord();
        rec.setFieldsFromJSONObject(e);
        stepsList.add(rec);
      }

      this.stepRecords.add(stepsList);
    }
  }
}
