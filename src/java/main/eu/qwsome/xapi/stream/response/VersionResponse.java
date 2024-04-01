package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.error.APIReplyParseException;

@Getter
@ToString
public class VersionResponse extends BaseResponse {

  private final String version;


  public VersionResponse(final String body) throws APIReplyParseException, XAPIResponseException {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    this.version = ob.getString("version");
  }
}
