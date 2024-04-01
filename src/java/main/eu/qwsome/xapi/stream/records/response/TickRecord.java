package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class TickRecord implements BaseResponseRecord {

  private double ask;
  private double bid;
  private long askVolume;
  private long bidVolume;
  private double high;
  private double low;
  private String symbol;
  private long timestamp;
  private int level;
  private double spreadRaw;
  private double spreadTable;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.ask = ob.getDouble("ask");
    this.bid = ob.getDouble("bid");
    this.askVolume = ob.optLong("askVolume");
    this.bidVolume = ob.optLong("bidVolume");

    this.high = ob.optDouble("high");
    this.low = ob.optDouble("low");

    this.symbol = ob.getString("symbol");

    this.timestamp = ob.getLong("timestamp");

    this.level = ob.getInt("level");

    this.spreadRaw = (Double) ob.get("spreadRaw");
    this.spreadTable = (Double) ob.get("spreadTable");
  }
}
