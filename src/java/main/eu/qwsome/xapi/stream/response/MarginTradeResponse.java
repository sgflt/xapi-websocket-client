package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class MarginTradeResponse extends BaseResponse {

  private double margin;


  public MarginTradeResponse(final String body) {
    super(body);

    final var rd = (JSONObject) this.getReturnData();
    if (rd != null) {
      this.margin = (Double) rd.get("margin");
    }
  }
}
