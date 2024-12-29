/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 Lukáš Kvídera
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
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

import eu.qwsome.xapi.error.XtbApiException;
import eu.qwsome.xapi.stream.ResponseParser;
import eu.qwsome.xapi.stream.subscription.balance.SBalanceRecord;
import eu.qwsome.xapi.stream.subscription.candle.SCandleRecord;
import eu.qwsome.xapi.stream.subscription.keepalive.SKeepAliveRecord;
import eu.qwsome.xapi.stream.subscription.news.SNewsRecord;
import eu.qwsome.xapi.stream.subscription.profit.SProfitRecord;
import eu.qwsome.xapi.stream.subscription.tick.STickRecord;
import eu.qwsome.xapi.stream.subscription.trade.STradeRecord;
import eu.qwsome.xapi.stream.subscription.trade.status.STradeStatusRecord;

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
        try {
          switch (brr) {
            case final SBalanceRecord balanceRecord -> this.balanceSubject.onNext(balanceRecord);
            case final STickRecord tickRecord -> this.priceSubject.onNext(tickRecord);
            case final STradeRecord tradeRecord -> this.tradeSubject.onNext(tradeRecord);
            case final STradeStatusRecord tradeStatusRecord -> this.tradeStatusSubject.onNext(tradeStatusRecord);
            case final SNewsRecord newsRecord -> this.newsSubject.onNext(newsRecord);
            case final SCandleRecord candleRecord -> this.candleSubject.onNext(candleRecord);
            case final SKeepAliveRecord keepAliveRecord -> this.keepAliveSubject.onNext(keepAliveRecord);
            case final SProfitRecord profitRecord -> this.profitsSubject.onNext(profitRecord);
            default -> log.error("Unknown command {}", command);
          }
        } catch (final XtbApiException e) {
          handleException(e);
        }
      }
    }
  }


  private void handleException(final XtbApiException e) {
    log.error("XtbApiException", e);

    this.balanceSubject.onError(e);
    this.priceSubject.onError(e);
    this.tradeSubject.onError(e);
    this.tradeStatusSubject.onError(e);
    this.newsSubject.onError(e);
    this.candleSubject.onError(e);
    this.keepAliveSubject.onError(e);
    this.profitsSubject.onError(e);
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
