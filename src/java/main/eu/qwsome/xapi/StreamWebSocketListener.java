package eu.qwsome.xapi;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import eu.qwsome.xapi.stream.records.response.SBalanceRecord;
import eu.qwsome.xapi.stream.records.response.SCandleRecord;
import eu.qwsome.xapi.stream.records.response.SKeepAliveRecord;
import eu.qwsome.xapi.stream.records.response.SNewsRecord;
import eu.qwsome.xapi.stream.records.response.SProfitRecord;
import eu.qwsome.xapi.stream.records.response.STickRecord;
import eu.qwsome.xapi.stream.records.response.STradeRecord;
import eu.qwsome.xapi.stream.records.response.STradeStatusRecord;
import eu.qwsome.xapi.stream.response.ResponseParser;

@Slf4j
public class StreamWebSocketListener extends WebSocketListener {

  private final PublishSubject<SBalanceRecord> balanceSubject = PublishSubject.create();
  private final PublishSubject<STickRecord> priceSubject = PublishSubject.create();
  private final PublishSubject<STradeRecord> tradeSubject = PublishSubject.create();
  private final PublishSubject<STradeStatusRecord> tradeStatusSubject = PublishSubject.create();
  private final PublishSubject<SNewsRecord> newsSubject = PublishSubject.create();
  private final PublishSubject<SCandleRecord> candleSubject = PublishSubject.create();
  private final PublishSubject<SKeepAliveRecord> keepAliveSubject = PublishSubject.create();
  private final PublishSubject<SProfitRecord> profitsSubject = PublishSubject.create();


  @Override
  public void onClosed(@NotNull final WebSocket webSocket, final int code, @NotNull final String reason) {
    this.balanceSubject.onComplete();
    this.priceSubject.onComplete();
    this.tradeSubject.onComplete();
    this.tradeStatusSubject.onComplete();
    this.newsSubject.onComplete();
    this.candleSubject.onComplete();
    this.keepAliveSubject.onComplete();
    this.profitsSubject.onComplete();
  }


  @Override
  public void onFailure(
      @NotNull final WebSocket webSocket,
      @NotNull final Throwable t,
      @Nullable final Response response
  ) {
    log.error("onFailure(webSocket={}, t={}, response={})", webSocket, t, response);

    this.balanceSubject.onError(t);
    this.priceSubject.onError(t);
    this.tradeSubject.onError(t);
    this.tradeStatusSubject.onError(t);
    this.newsSubject.onError(t);
    this.candleSubject.onError(t);
    this.keepAliveSubject.onError(t);
    this.profitsSubject.onError(t);
  }


  @Override
  public void onMessage(@NotNull final WebSocket webSocket, @NotNull final String text) {
    log.trace("onMessage {}", text);

    final var jsonObject = new JSONObject(text);
    if (jsonObject.keySet().contains("command") && jsonObject.keySet().contains("data")) {
      final var command = (String) jsonObject.get("command");
      final var data = (JSONObject) jsonObject.get("data");
      if (command != null) {
        final var brr = new ResponseParser().parse(command, data);
        // TODO parse in constructor
        brr.setFieldsFromJSONObject(data);

        if (brr instanceof final SBalanceRecord balanceRecord) {
          this.balanceSubject.onNext(balanceRecord);
        } else if (brr instanceof final STickRecord tickRecord) {
          this.priceSubject.onNext(tickRecord);
        } else if (brr instanceof final STradeRecord tradeRecord) {
          this.tradeSubject.onNext(tradeRecord);
        } else if (brr instanceof final STradeStatusRecord tradeStatusRecord) {
          this.tradeStatusSubject.onNext(tradeStatusRecord);
        } else if (brr instanceof final SNewsRecord newsRecord) {
          this.newsSubject.onNext(newsRecord);
        } else if (brr instanceof final SCandleRecord candleRecord) {
          this.candleSubject.onNext(candleRecord);
        } else if (brr instanceof final SKeepAliveRecord keepAliveRecord) {
          this.keepAliveSubject.onNext(keepAliveRecord);
        } else if (brr instanceof final SProfitRecord profitRecord) {
          this.profitsSubject.onNext(profitRecord);
        }
      }
    }
  }


  public Observable<SBalanceRecord> createBalanceStream() {
    return this.balanceSubject;
  }


  public Observable<SCandleRecord> createCandleStream() {
    return this.candleSubject;
  }


  public Observable<STickRecord> createPriceStream() {
    return this.priceSubject;
  }


  public Observable<STradeRecord> createTradesStream() {
    return this.tradeSubject;
  }


  public Observable<SNewsRecord> createNewsStream() {
    return this.newsSubject;
  }


  public void unsubscribePrice() {
    this.priceSubject.onComplete();
  }


  public Observable<SKeepAliveRecord> createKeepAliveStream() {
    return this.keepAliveSubject;
  }


  public Observable<STradeStatusRecord> createTradesStatusStream() {
    return this.tradeStatusSubject;
  }


  public void unsubscribeTrades() {
    this.tradeSubject.onComplete();
  }


  public void unsubscribeNews() {
    this.newsSubject.onComplete();
  }


  public Observable<SProfitRecord> subscribeProfits() {
    return this.profitsSubject;
  }


  public void unsubscribeProfits() {
    this.profitsSubject.onComplete();
  }


  public void unsubscribeBalance() {
    this.balanceSubject.onComplete();
  }


  public void unsubscribeCandle() {
    this.candleSubject.onComplete();
  }


  public void unsubscribeKeepAlive() {
    this.keepAliveSubject.onComplete();
  }


  public void unsubscribeTradeStatus() {
    this.tradeStatusSubject.onComplete();
  }
}
