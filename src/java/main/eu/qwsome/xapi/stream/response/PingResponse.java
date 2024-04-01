package eu.qwsome.xapi.stream.response;


import lombok.ToString;

@ToString
public class PingResponse extends BaseResponse {

  public PingResponse(final String body) {
    super(body);
  }
}
