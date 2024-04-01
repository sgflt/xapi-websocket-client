package eu.qwsome.xapi.sync;

import lombok.Getter;
import org.json.JSONObject;

@Getter
public class Credentials {

  private final String login;
  private final String password;
  private String appId;
  private String appName;


  public Credentials(final String login, final String password) {
    this.login = login;
    this.password = password;
  }


  public Credentials(final String login, final String password, final String appName) {
    this(login, password);
    this.appName = appName;
  }


  public Credentials(final String login, final String password, final String appId, final String appName) {
    this(login, password, appName);
    this.appId = appId;
  }


  public JSONObject toJson() {
    final var response = new JSONObject();
    response.put("userId", getLogin());
    response.put("password", getPassword());
    response.put("type", "java");
    response.put("version", "eu.qwsome.xtb-api");

    if (this.appId != null) {
      response.put("appId", this.appId);
    }

    if (this.appName != null) {
      response.put("appName", this.appName);
    }
    return response;
  }
}
