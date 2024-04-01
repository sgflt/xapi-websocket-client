package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.TradeRecord;

@Getter
@ToString
public class TradesHistoryResponse extends BaseResponse {

  private final List<TradeRecord> tradeRecords;


  public TradesHistoryResponse(final String body) {
    super(body);

    final var arr = (JSONArray) this.getReturnData();
    this.tradeRecords = new ArrayList<>(arr.length());

    for (final Object o : arr) {
      final var e = (JSONObject) o;
      final var tradeRecord = new TradeRecord();
      tradeRecord.setFieldsFromJSONObject(e);
      this.tradeRecords.add(tradeRecord);
    }
  }
}
