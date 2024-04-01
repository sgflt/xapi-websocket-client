package eu.qwsome.xapi.stream.subscription;


import lombok.experimental.SuperBuilder;
import org.json.JSONObject;

@SuperBuilder
public abstract class StreamingCommandRecord {

  public final String toJSONString() {
    final var obj = new JSONObject();
    obj.put("command", this.getCommand());

    final String extraKey = this.getExtraKey();
    if (!extraKey.isEmpty()) {
      obj.put(extraKey, getExtraValue());
    }

    final String extraKey2 = this.getExtraKey2();
    if (!extraKey2.isEmpty()) {
      obj.put(extraKey2, getExtraIntValue2());
    }

    final String extraKey3 = this.getExtraKey3();
    if (!extraKey3.isEmpty()) {
      obj.put(extraKey3, getExtraIntValue3());
    }

    final String streamSessionId = getStreamSessionId();
    if (!streamSessionId.isEmpty()) {
      obj.put("streamSessionId", streamSessionId);
    }
    return obj.toString();
  }


  protected String getExtraKey() {
    return "";
  }


  protected String getExtraValue() {
    return "";
  }


  protected String getExtraKey2() {
    return "";
  }


  protected int getExtraIntValue2() {
    return 0;
  }


  protected String getExtraKey3() {
    return "";
  }


  protected int getExtraIntValue3() {
    return 0;
  }


  protected String getStreamSessionId() {
    return "";
  }


  protected abstract String getCommand();
}
