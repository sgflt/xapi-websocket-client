package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.error.APIReplyParseException;
import eu.qwsome.xapi.stream.records.response.TickRecord;

@Getter
@ToString
public class TickPricesResponse extends BaseResponse {

  private final List<TickRecord> ticks;


  public TickPricesResponse(final String body) throws APIReplyParseException, XAPIResponseException {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    final var arr = (JSONArray) ob.get("quotations");
    this.ticks = new ArrayList<>(arr.length());

    for (final var o : arr) {
      final var e = (JSONObject) o;
      final var tickRecord = new TickRecord();
      tickRecord.setFieldsFromJSONObject(e);
      this.ticks.add(tickRecord);
    }
  }
}
