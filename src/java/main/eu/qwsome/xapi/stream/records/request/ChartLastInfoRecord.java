package eu.qwsome.xapi.stream.records.request;

import lombok.Data;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.PeriodCode;

@Data
public class ChartLastInfoRecord {

  private final String symbol;
  private final PeriodCode period;
  private final long start;


  public JSONObject toJSONObject() {
    final var obj = new JSONObject();
    obj.put("symbol", this.symbol);
    obj.put("period", this.period.getCode());
    obj.put("start", this.start);
    return obj;
  }
}
