package eu.qwsome.xapi.stream.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.SymbolRecord;

@Getter
@ToString
public class AllSymbolsResponse extends BaseResponse {

  private final List<SymbolRecord> symbolRecords;


  public AllSymbolsResponse(final String body) {
    super(body);
    final var symbols = (JSONArray) this.getReturnData();
    this.symbolRecords = new ArrayList<>(symbols.length());
    for (final var symbol : symbols) {
      final JSONObject e = (JSONObject) symbol;
      final var symbolRecord = new SymbolRecord();
      symbolRecord.setFieldsFromJSONObject(e);
      this.symbolRecords.add(symbolRecord);
    }
  }
}
