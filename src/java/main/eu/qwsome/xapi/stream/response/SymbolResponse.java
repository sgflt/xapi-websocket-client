package eu.qwsome.xapi.stream.response;


import lombok.Getter;
import lombok.ToString;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.SymbolRecord;

@Getter
@ToString
public class SymbolResponse extends BaseResponse {

  private final SymbolRecord symbol;


  public SymbolResponse(final String body) {
    super(body);

    final var ob = (JSONObject) this.getReturnData();
    this.symbol = new SymbolRecord();
    this.symbol.setFieldsFromJSONObject(ob);
  }
}
