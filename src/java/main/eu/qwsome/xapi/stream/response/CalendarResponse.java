package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.CalendarRecord;

@ToString
@Getter
public class CalendarResponse extends BaseResponse {

  private final List<CalendarRecord> calendarRecords;


  public CalendarResponse(final String body) {
    super(body);
    final JSONArray arr = (JSONArray) this.getReturnData();
    this.calendarRecords = new ArrayList<>(arr.length());

    for (final var o : arr) {
      final var e = (JSONObject) o;
      final var calendarRecord = new CalendarRecord();
      calendarRecord.setFieldsFromJSONObject(e);
      this.calendarRecords.add(calendarRecord);
    }
  }
}
