package eu.qwsome.xapi.stream.response;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONException;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.codes.ErrorCode;
import eu.qwsome.xapi.stream.error.APIReplyParseException;

@Getter
@ToString
public class BaseResponse {

  private final Boolean status;
  private final String errorDescr;
  private final ErrorCode errCode;
  private final Object returnData;
  private JSONObject redirectJson;
  private final String streamSessionId;


  public BaseResponse(final String body) {
    try {
      final var ob = new JSONObject(body);
      this.status = (Boolean) ob.get("status");
      this.errCode = ob.has("errorCode") ? new ErrorCode(ob.getString("errorCode")) : null;
      this.errorDescr = ob.has("errorDescr") ? ob.getString("errorDescr") : null;
      this.returnData = ob.has("returnData") ? ob.get("returnData") : null;
      this.streamSessionId = ob.getString("streamSessionId");

      parseRedirect(ob);
    } catch (final JSONException e) {
      throw new APIReplyParseException("JSON Parse exception: " + body);
    }
  }


  private void parseRedirect(final JSONObject ob) {
    try {
      this.redirectJson = (JSONObject) ob.get("redirect");
    } catch (final Exception ignore) {
      this.redirectJson = null;
    }
  }
}
