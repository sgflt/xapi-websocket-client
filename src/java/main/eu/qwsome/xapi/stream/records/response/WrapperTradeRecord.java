package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
abstract class WrapperTradeRecord implements BaseResponseRecord {

  private double close_price;
  private boolean closed;
  private int cmd;
  private String comment;
  private String customComment;
  private double commission;
  private long order;
  private long order2;
  private double volume;
  private double margin_rate;
  private double open_price;
  private String symbol;
  private double storage;
  private int digits;

  protected Long close_time;
  protected Long expiration;
  protected long position;
  protected Double profit;
  protected double sl;
  protected double tp;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.close_price = ob.getDouble("close_price");
    this.closed = ob.getBoolean("closed");
    this.cmd = ob.getInt("cmd");
    this.comment = ob.getString("comment");
    this.customComment = ob.optString("customComment");
    this.commission = ob.getDouble("commission");
    this.order = ob.getLong("order");
    this.order2 = ob.getLong("order2");
    this.volume = ob.getDouble("volume");
    this.margin_rate = ob.getDouble("margin_rate");
    this.open_price = ob.getDouble("open_price");
    this.symbol = ob.getString("symbol");
    this.storage = ob.getDouble("storage");
    this.digits = ob.getInt("digits");
  }
}
