package eu.qwsome.xapi.stream.records.request;

import lombok.Builder;
import lombok.Data;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.TradeOperationCode;
import eu.qwsome.xapi.stream.codes.TradeTransactionType;

@Data
@Builder
public class TradeTransInfoRecord {

  private final TradeOperationCode cmd;
  private final TradeTransactionType type;
  private final Double price;
  private final Double sl;
  private final Double tp;
  private final String symbol;
  private final Double volume;
  private final Long order;
  private final Long expiration;
  private final Double offset;
  private final String customComment;


  public JSONObject toJSONObject() {
    final var obj = new JSONObject();
    obj.put("cmd", this.cmd.getCode());
    obj.put("customComment", this.customComment);
    obj.put("expiration", this.expiration);
    obj.put("offset", this.offset);
    obj.put("order", this.order);
    obj.put("price", this.price);
    obj.put("sl", this.sl);
    obj.put("symbol", this.symbol);
    obj.put("tp", this.tp);
    obj.put("type", this.type.getCode());
    obj.put("volume", this.volume);

    return new JSONObject().put("tradeTransInfo", obj);
  }
}
