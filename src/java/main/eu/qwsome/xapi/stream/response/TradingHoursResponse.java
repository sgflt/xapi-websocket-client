package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

@Getter
@ToString
public class TradingHoursResponse extends BaseResponse {

  private final List<String> symbols;
  private final List<List<HoursRecord>> quotes = new LinkedList<List<HoursRecord>>();
  private final List<List<HoursRecord>> trading = new LinkedList<List<HoursRecord>>();


  public TradingHoursResponse(final String body) {
    super(body);

    final var aHR = (JSONArray) this.getReturnData();
    this.symbols = new ArrayList<>(aHR.length());

    for (final var o : aHR) {
      final var ob = (JSONObject) o;
      final var symbol = ob.getString("symbol");
      this.symbols.add(symbol);

      final var qHR = (JSONArray) ob.get("quotes");
      final var quotesList = new ArrayList<HoursRecord>(qHR.length());
      for (final var e : qHR) {
        final var rec = new HoursRecord();
        rec.setFieldsFromJSONObject((JSONObject) e);
        quotesList.add(rec);
      }
      this.quotes.add(quotesList);

      final var tHR = (JSONArray) ob.get("trading");
      final var tradingList = new ArrayList<HoursRecord>(tHR.length());
      for (final var e : tHR) {
        final var rec = new HoursRecord();
        rec.setFieldsFromJSONObject((JSONObject) e);
        tradingList.add(rec);
      }

      this.trading.add(tradingList);
    }
  }
}
