package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class RateInfoRecord implements BaseResponseRecord {

  private long ctm;
  private double open;
  private double high;
  private double low;
  private double close;
  private double vol;


  public RateInfoRecord() {
  }


  public void setCtm(final long ctm) {
    this.ctm = ctm;
  }


  public void setOpen(final double open) {
    this.open = open;
  }


  public void setHigh(final double high) {
    this.high = high;
  }


  public void setLow(final double low) {
    this.low = low;
  }


  public void setClose(final double close) {
    this.close = close;
  }


  public void setVol(final double vol) {
    this.vol = vol;
  }


  @Override
  public void setFieldsFromJSONObject(final JSONObject e) {
    // TODO
    this.setClose((Double) e.get("close"));
    this.setCtm((Long) e.get("ctm"));
    this.setHigh((Double) e.get("high"));
    this.setLow((Double) e.get("low"));
    this.setOpen((Double) e.get("open"));
    this.setVol((Double) e.get("vol"));
  }
}
