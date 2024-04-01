package eu.qwsome.xapi.stream.records.response;


import org.json.JSONObject;

public interface BaseResponseRecord {
  void setFieldsFromJSONObject(JSONObject ob);
}
