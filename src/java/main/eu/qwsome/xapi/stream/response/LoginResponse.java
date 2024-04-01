package eu.qwsome.xapi.stream.response;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.RedirectRecord;

@Getter
@ToString
public class LoginResponse extends BaseResponse {

  private RedirectRecord redirect;


  public LoginResponse(final String body) {
    super(body);
    final JSONObject redirectJson = super.getRedirectJson();
    if (redirectJson != null) {
      this.redirect = new RedirectRecord();
      this.redirect.setFieldsFromJSONObject(redirectJson);
    }
  }


  public boolean isRedirectSet() {
    return this.redirect != null;
  }
}
