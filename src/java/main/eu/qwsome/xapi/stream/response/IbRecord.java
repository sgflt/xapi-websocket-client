package eu.qwsome.xapi.stream.response;


import lombok.Data;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.BaseResponseRecord;

@Data
public class IbRecord implements BaseResponseRecord {

  private String surname;
  private Integer side;
  private Double openPrice;
  private Double nominal;
  private Double closePrice;
  private String symbol;
  private Double volume;
  private String login;
  private Long timestamp;


  @Override
  public void setFieldsFromJSONObject(final JSONObject ob) {
    if (ob != null) {
      this.symbol = ob.getString("symbol");
      this.surname = ob.getString("surname");
      this.login = ob.getString("login");
      this.openPrice = (Double) ob.get("openPrice");
      this.nominal = (Double) ob.get("nominal");
      this.closePrice = (Double) ob.get("closePrice");
      this.volume = (Double) ob.get("volume");
      this.timestamp = ob.getLong("timestamp");
      this.side = ob.getInt("side");
    }
  }
}
