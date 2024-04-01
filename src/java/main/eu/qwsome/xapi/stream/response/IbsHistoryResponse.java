package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

@Getter
@ToString
public class IbsHistoryResponse extends BaseResponse {

  private final List<IbRecord> ibRecords;


  public IbsHistoryResponse(final String body) {
    super(body);
    final var ib = (JSONArray) this.getReturnData();
    this.ibRecords = new ArrayList<>(ib.length());

    for (final var o : ib) {
      final var e = (JSONObject) o;
      final var ibRecord = new IbRecord();
      ibRecord.setFieldsFromJSONObject(e);
      this.ibRecords.add(ibRecord);
    }
  }
}
