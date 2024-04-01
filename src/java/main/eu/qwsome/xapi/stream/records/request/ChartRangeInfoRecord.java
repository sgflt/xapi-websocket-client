package eu.qwsome.xapi.stream.records.request;

import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.PeriodCode;

@Data
@Builder
public class ChartRangeInfoRecord {

  private final String symbol;
  private final PeriodCode period;
  private final long start;
  private final long end;
  private final long ticks;


  public JSONObject toJSONObject() {
    final var obj = new JSONObject();
    obj.put("symbol", this.symbol);
    obj.put("period", this.period.getCode());
    obj.put("start", this.start);
    obj.put("end", this.end);
    obj.put("ticks", this.ticks);
    return obj;
  }
}
