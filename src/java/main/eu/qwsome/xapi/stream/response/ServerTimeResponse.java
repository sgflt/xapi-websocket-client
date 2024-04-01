package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class ServerTimeResponse extends BaseResponse {

  private final long time;


  public ServerTimeResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    this.time = ob.getLong("time");
  }
}
