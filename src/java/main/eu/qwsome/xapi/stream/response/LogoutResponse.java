package eu.qwsome.xapi.stream.response;


public class LogoutResponse extends BaseResponse {

  public LogoutResponse(final String body) {
    super(body);
  }


  @Override
  public String toString() {
    return "LogoutResponse{" + '}';
  }
}
