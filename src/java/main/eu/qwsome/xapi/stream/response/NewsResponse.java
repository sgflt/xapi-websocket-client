package eu.qwsome.xapi.stream.response;


import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.ToString;
import org.json.JSONArray;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.NewsTopicRecord;

@Getter
@ToString
public class NewsResponse extends BaseResponse {

  private final List<NewsTopicRecord> newsTopicRecords;


  public NewsResponse(final String body) {
    super(body);

    final var arr = (JSONArray) this.getReturnData();
    this.newsTopicRecords = new ArrayList<>(arr.length());

    for (final var o : arr) {
      final var e = (JSONObject) o;
      final var newsTopicRecord = new NewsTopicRecord();
      newsTopicRecord.setFieldsFromJSONObject(e);
      this.newsTopicRecords.add(newsTopicRecord);
    }
  }
}
