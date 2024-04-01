package eu.qwsome.xapi.stream.records.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class SBalanceRecord implements BaseResponseRecord {

  private double balance;
  private double margin;
  private double equity;
  private double marginLevel;
  private double marginFree;
  private double credit;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    this.balance = ob.getDouble("balance");
    this.margin = ob.getDouble("margin");
    this.credit = ob.getDouble("credit");
    this.equity = ob.getDouble("equity");
    this.marginLevel = ob.getDouble("marginLevel");
    this.marginFree = ob.getDouble("marginFree");
  }
}
