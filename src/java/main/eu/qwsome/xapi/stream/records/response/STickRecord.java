package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class STickRecord implements BaseResponseRecord {

  private double ask;
  private double bid;
  private Integer askVolume;
  private Integer bidVolume;
  private double high;
  private double low;
  private double spreadRaw;
  private double spreadTable;
  private String symbol;
  private int quoteId;
  private int level;
  private long timestamp;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.ask = ob.getDouble("ask");
    this.bid = ob.getDouble("bid");

    this.spreadRaw = ob.getDouble("spreadRaw");
    this.spreadTable = ob.getDouble("spreadTable");

    this.askVolume = ob.getInt("askVolume");
    this.bidVolume = ob.getInt("bidVolume");

    this.high = ob.getDouble("high");
    this.low = ob.getDouble("low");

    this.symbol = ob.getString("symbol");
    this.timestamp = ob.getLong("timestamp");
    this.level = ob.getInt("level");
    this.quoteId = ob.getInt("quoteId");
  }
}
