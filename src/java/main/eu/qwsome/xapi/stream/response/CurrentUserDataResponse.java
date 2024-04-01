package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

@Getter
@ToString
public class CurrentUserDataResponse extends BaseResponse {

  private final String currency;
  private final String group;
  private final double leverageMultiplier;
  private final int companyUnit;
  private final String spreadType;
  private final boolean ibAccount;


  public CurrentUserDataResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    this.currency = ob.getString("currency");
    this.spreadType = ob.getString("spreadType");
    this.group = ob.getString("group");
    this.leverageMultiplier = ob.getDouble("leverageMultiplier");
    this.companyUnit = ob.getInt("companyUnit");
    this.ibAccount = ob.getBoolean("ibAccount");
  }
}
