package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class SCandleRecord implements BaseResponseRecord {

  private long ctm;
  private String ctmString;
  private double open;
  private double high;
  private double low;
  private double close;
  private double vol;
  private int quoteId;
  private String symbol;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.ctm = ob.getLong("ctm");
    this.ctmString = ob.getString("ctmString");
    this.symbol = ob.getString("symbol");
    this.quoteId = ob.getInt("quoteId");
    this.open = ob.getDouble("open");
    this.high = ob.getDouble("high");
    this.low = ob.getDouble("low");
    this.close = ob.getDouble("close");
    this.vol = ob.getDouble("vol");
  }
}
