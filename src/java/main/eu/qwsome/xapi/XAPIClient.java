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

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import io.github.bucket4j.BlockingBucket;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.local.SynchronizationStrategy;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import org.jetbrains.annotations.NotNull;

import eu.qwsome.xapi.codes.TradeOperationCode;
import eu.qwsome.xapi.stream.subscription.StreamPingCommand;
import eu.qwsome.xapi.stream.subscription.balance.BalanceStop;
import eu.qwsome.xapi.stream.subscription.balance.BalanceSubscribe;
import eu.qwsome.xapi.stream.subscription.balance.SBalanceRecord;
import eu.qwsome.xapi.stream.subscription.candle.CandlesStop;
import eu.qwsome.xapi.stream.subscription.candle.CandlesSubscribe;
import eu.qwsome.xapi.stream.subscription.candle.SCandleRecord;
import eu.qwsome.xapi.stream.subscription.keepalive.KeepAliveStop;
import eu.qwsome.xapi.stream.subscription.keepalive.KeepAliveSubscribe;
import eu.qwsome.xapi.stream.subscription.keepalive.SKeepAliveRecord;
import eu.qwsome.xapi.stream.subscription.news.NewsStop;
import eu.qwsome.xapi.stream.subscription.news.NewsSubscribe;
import eu.qwsome.xapi.stream.subscription.news.SNewsRecord;
import eu.qwsome.xapi.stream.subscription.profit.ProfitsStop;
import eu.qwsome.xapi.stream.subscription.profit.ProfitsSubscribe;
import eu.qwsome.xapi.stream.subscription.profit.SProfitRecord;
import eu.qwsome.xapi.stream.subscription.tick.STickRecord;
import eu.qwsome.xapi.stream.subscription.tick.TickPricesStop;
import eu.qwsome.xapi.stream.subscription.tick.TickPricesSubscribe;
import eu.qwsome.xapi.stream.subscription.trade.STradeRecord;
import eu.qwsome.xapi.stream.subscription.trade.TradeRecordsStop;
import eu.qwsome.xapi.stream.subscription.trade.TradeRecordsSubscribe;
import eu.qwsome.xapi.stream.subscription.trade.status.STradeStatusRecord;
import eu.qwsome.xapi.stream.subscription.trade.status.TradeStatusRecordsStop;
import eu.qwsome.xapi.stream.subscription.trade.status.TradeStatusRecordsSubscribe;
import eu.qwsome.xapi.sync.chart.ChartLastCommand;
import eu.qwsome.xapi.sync.chart.ChartLastInfoRecord;
import eu.qwsome.xapi.sync.chart.ChartResponse;
import eu.qwsome.xapi.sync.chart.PeriodCode;
import eu.qwsome.xapi.sync.currentuserdata.CurrentUserDataCommand;
import eu.qwsome.xapi.sync.currentuserdata.CurrentUserDataResponse;
import eu.qwsome.xapi.sync.login.Credentials;
import eu.qwsome.xapi.sync.login.LoginCommand;
import eu.qwsome.xapi.sync.login.LoginResponse;
import eu.qwsome.xapi.sync.margin.level.MarginLevelCommand;
import eu.qwsome.xapi.sync.margin.level.MarginLevelResponse;
import eu.qwsome.xapi.sync.ping.PingCommand;
import eu.qwsome.xapi.sync.symbol.SymbolCommand;
import eu.qwsome.xapi.sync.symbol.SymbolResponse;
import eu.qwsome.xapi.sync.symbol.all.AllSymbolsCommand;
import eu.qwsome.xapi.sync.symbol.all.AllSymbolsResponse;
import eu.qwsome.xapi.sync.trade.TradeRecordsResponse;
import eu.qwsome.xapi.sync.trade.TradesCommand;
import eu.qwsome.xapi.sync.transaction.TradeTransInfoRecord;
import eu.qwsome.xapi.sync.transaction.TradeTransactionCommand;
import eu.qwsome.xapi.sync.transaction.TradeTransactionResponse;
import eu.qwsome.xapi.sync.transaction.TradeTransactionType;
import eu.qwsome.xapi.sync.transaction.status.TradeTransactionStatusCommand;
import eu.qwsome.xapi.sync.transaction.status.TradeTransactionStatusResponse;

/**
 * Client that is usable for single symbol trading
 */
@Slf4j
public class XAPIClient {

  private final OkHttpClient client = new OkHttpClient.Builder().build();

  private final BlockingBucket bucket = Bucket.builder()
      .withSynchronizationStrategy(SynchronizationStrategy.SYNCHRONIZED)
      .addLimit(limit -> limit.capacity(3).refillIntervally(1, Duration.ofMillis(250)))
      .build().asBlocking();

  private final String symbol;
  private final String url;

  private String sessionId;
  private WebSocket syncWebsocket;

  private WebSocket streamWebsocket;
  private final StreamWebSocketListener streamWebSocketListener = new StreamWebSocketListener();
  private final MainWebsocketListener syncListener = new MainWebsocketListener();


  public XAPIClient(final String symbol, final String url) {
    this.symbol = symbol;
    this.url = url;
  }


  public @NonNull Single<LoginResponse> connect(final Credentials credentials) {
    final var request = new Request.Builder()
        .url(this.url)
        .build();

    this.syncWebsocket = this.client.newWebSocket(request, this.syncListener);
    return login(credentials);
  }


  private void keepAlive(final Long iteration) {
    log.trace("keepAlive(iteration={})", iteration);

    this.bucket.consumeUninterruptibly(1);

    this.syncListener.setCommand(MainWebsocketListener.Command.PING);
    this.syncWebsocket.send(new PingCommand().toJSONString());

    sendCommand(StreamPingCommand.builder()
        .streamSessionId(this.sessionId)
        .build().toJSONString());
  }


  private @NonNull Single<LoginResponse> login(final Credentials credentials) {
    log.trace("login(credentials={})", credentials);

    return this.syncListener.createLoginStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.LOGIN);
          this.syncWebsocket.send(new LoginCommand(credentials).toJSONString());
        }).doOnSuccess(loginResponse -> {
          connectStream(loginResponse.getStreamSessionId());

          Observable.interval(30, TimeUnit.SECONDS)
              .subscribe(this::keepAlive, throwable -> log.error("keep alive failed", throwable));
        })
        ;
  }


  private void connectStream(final String sessionId) {
    log.trace("connectStream(sessionId={})", sessionId);

    this.bucket.consumeUninterruptibly(1);

    this.sessionId = sessionId;
    final var request = new Request.Builder()
        .url(this.url.concat("Stream"))
        .build();
    this.streamWebsocket = this.client.newWebSocket(request, this.streamWebSocketListener);
  }


  public AllSymbolsResponse getAllSymbols() {
    log.trace("getAllSymbols()");

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createAllSymbolsStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_ALL_SYMBOLS);
          this.syncWebsocket.send(new AllSymbolsCommand().toJSONString());
        })
        .blockingGet();
  }


  public CurrentUserDataResponse getCurrentUserData() {
    log.trace("getCurrentUserData()");

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createGetCurrentUserDataStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_USER_DATA);
          this.syncWebsocket.send(new CurrentUserDataCommand().toJSONString());
        }).blockingGet();
  }


  public MarginLevelResponse getMarginLevel() {
    log.trace("getMarginLevel()");

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createGeMarginLevelStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_MARGIN_LEVEL);
          this.syncWebsocket.send(new MarginLevelCommand().toJSONString());
        }).blockingGet();
  }


  public SymbolResponse getSymbol() {
    return getSymbol(this.symbol);
  }


  public SymbolResponse getSymbol(final String symbol) {
    log.trace("getSymbol(symbol={})", symbol);

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createGetSymbolStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_SYMBOL);
          this.syncWebsocket.send(new SymbolCommand(symbol).toJSONString());
        }).blockingGet();
  }


  public TradeRecordsResponse getOpenedTrades() {
    log.trace("getOpenedTrades()");

    this.bucket.consumeUninterruptibly(1);

    return getTrades(true);
  }


  public TradeRecordsResponse getAllTrades() {
    log.trace("getAllTrades()");

    this.bucket.consumeUninterruptibly(1);

    return getTrades(false);
  }


  public @NonNull Single<ChartResponse> getChartLastRequest(final Instant start, final PeriodCode period) {
    return getChartLastRequest(this.symbol, start, period);
  }


  public @NonNull Single<ChartResponse> getChartLastRequest(
      final String symbol,
      final Instant start,
      final PeriodCode period
  ) {
    log.trace("getChartLastRequest(symbol={}, start={}, period={})", symbol, start, period);

    this.bucket.consumeUninterruptibly(1);

    final var limitedStart = switch (period) {
      case M1, M5, M15 ->
          start.isBefore(Instant.now().minus(Duration.ofDays(30))) ? Instant.now().minus(Duration.ofDays(30)) : start;
      case M30, H1 -> start.isBefore(Instant.now().minus(Duration.ofDays(7L * 30)))
                      ? Instant.now().minus(Duration.ofDays(7L * 30))
                      : start;
      case H4, D1, W1, MN1 -> start.isBefore(Instant.now().minus(Duration.ofDays(13L * 30))) ? Instant.now()
          .minus(Duration.ofDays(13L * 30)) : start;
    };

    return this.syncListener.createGetChartLastRequestStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_CHART);
          this.syncWebsocket.send(new ChartLastCommand(
              ChartLastInfoRecord.builder()
                  .symbol(symbol)
                  .start(limitedStart.toEpochMilli())
                  .period(period)
                  .build()
          ).toJSONString());
        });
  }


  private @NotNull TradeRecordsResponse getTrades(final boolean openedOnly) {
    log.trace("getTrades(openedOnly={})", openedOnly);

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createGetTradesStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_TRADES);
          this.syncWebsocket.send(new TradesCommand(openedOnly).toJSONString());
        }).blockingGet();
  }


  public TradeTransactionStatusResponse getTransactionStatus(final long orderId) {
    log.trace("getTransactionStatus()");

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createTradeTransactionStatusStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand(MainWebsocketListener.Command.GET_TRANSACTION_STATUS);
          this.syncWebsocket.send(new TradeTransactionStatusCommand(orderId).toJSONString());
        }).blockingGet();
  }


  public TradeTransactionResponse operatePosition(final TradeTransInfoRecord tradeTransInfoRecord) {
    log.debug("operatePosition(tradeTransInfoRecord={})", tradeTransInfoRecord);

    this.bucket.consumeUninterruptibly(1);

    return this.syncListener.createTradeTransactionStream()
        .doOnSubscribe(disposable -> {
          if (
              tradeTransInfoRecord.getCmd().equals(TradeOperationCode.BUY)
              && tradeTransInfoRecord.getType().equals(TradeTransactionType.OPEN)) {
            this.syncListener.setCommand(MainWebsocketListener.Command.BUY);
          } else if (
              tradeTransInfoRecord.getCmd().equals(TradeOperationCode.SELL)
              && tradeTransInfoRecord.getType().equals(TradeTransactionType.OPEN)) {
            this.syncListener.setCommand(MainWebsocketListener.Command.SELL);
          } else if (
              tradeTransInfoRecord.getCmd().equals(TradeOperationCode.BUY)
              && tradeTransInfoRecord.getType().equals(TradeTransactionType.CLOSE)) {
            this.syncListener.setCommand(MainWebsocketListener.Command.CLOSE_BUY);
          } else if (
              tradeTransInfoRecord.getCmd().equals(TradeOperationCode.SELL)
              && tradeTransInfoRecord.getType().equals(TradeTransactionType.CLOSE)) {
            this.syncListener.setCommand(MainWebsocketListener.Command.CLOSE_SELL);
          }
          this.syncWebsocket.send(new TradeTransactionCommand(tradeTransInfoRecord).toJSONString());
        })
        .blockingGet();
  }


  public static void main(final String[] args) {
    final var xapiClient = new XAPIClient("ETHEREUM", "wss://ws.xtb.com/demo");

    xapiClient.connect(new Credentials(
            System.getenv("LOGIN"),
            System.getenv("PASSWORD")
        ))
        .subscribe(
            loginResponse -> {
              xapiClient.createBalanceStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              xapiClient.createCandleStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              xapiClient.createPriceStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              xapiClient.createTradesStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              xapiClient.createTradesStatusStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              xapiClient.createProfitsStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              xapiClient.createTradesStatusStream()
                  .subscribeOn(Schedulers.io())
                  .subscribe(x -> log.info("{}", x));

              final var x = xapiClient.getSymbol();
              log.info("getSymbol {}", x);

              final var y = xapiClient.getAllSymbols();
              log.info("getAllSymbols {}", y);


              Observable.interval(1, TimeUnit.SECONDS)
                  .subscribe(
                      timer -> log.info("xaaxaxax {}", timer),
                      throwable -> log.error("eeeeee{}", throwable)
                  );
            },
            throwable -> log.error("Login failed", throwable)
        );
  }


  private void sendCommand(final String json) {
    this.bucket.consumeUninterruptibly(1);

    this.streamWebsocket.send(json);
  }


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds
   */
  public Observable<STickRecord> createPriceStream() {
    log.trace("createPriceStream()");

    return createPriceStream(this.symbol, 1000);
  }


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds
   */
  public Observable<STickRecord> createPriceStream(final int minArrivalTime) {
    log.trace("createPriceStream(minArrivalTime={})", minArrivalTime);

    return createPriceStream(this.symbol, minArrivalTime, 0);
  }


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds
   */
  public Observable<STickRecord> createPriceStream(final String symbol, final int minArrivalTime) {
    log.trace("createPriceStream(minArrivalTime={})", minArrivalTime);

    return createPriceStream(symbol, minArrivalTime, 0);
  }


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds and the given maxLevel
   */
  public Observable<STickRecord> createPriceStream(
      final String symbol,
      final int minArrivalTime,
      final int maxLevel
  ) {
    log.trace("createPriceStream(minArrivalTime={}, maxLevel={})", minArrivalTime, maxLevel);

    if (symbol != null) {
      subscribePriceStream(symbol, minArrivalTime, maxLevel);
    }

    return this.streamWebSocketListener.createPriceStream();
  }


  public void subscribePriceStream(final String symbol) {
    log.trace("subscribePriceStream(symbol={})", symbol);

    subscribePriceStream(symbol, 1000);
  }


  public void subscribePriceStream(final String symbol, final int minArrivalTime) {
    log.trace("subscribePriceStream(symbol={}, minArrivalTIme={})", symbol, minArrivalTime);

    subscribePriceStream(symbol, minArrivalTime, 0);
  }


  public void subscribePriceStream(final String symbol, final int minArrivalTime, final int maxLevel) {
    log.trace("subscribePriceStream(symbol={}, minArrivalTime={}, maxLevel={})", symbol, minArrivalTime, maxLevel);

    sendCommand(
        TickPricesSubscribe.builder()
            .symbol(symbol)
            .streamSessionId(this.sessionId)
            .minArrivalTime(minArrivalTime)
            .maxLevel(maxLevel)
            .build()
            .toJSONString()
    );
  }


  /**
   * Unsubscribes prices of a symbol
   */
  public void unsubscribePrice() {
    log.trace("unsubscribePrice()");

    unsubscribePrice(this.symbol);
  }


  /**
   * Unsubscribes prices of a symbol
   */
  public void unsubscribePrice(final String symbol) {
    log.trace("unsubscribePrice()");

    sendCommand(TickPricesStop.builder()
        .symbol(symbol)
        .build()
        .toJSONString());

    this.streamWebSocketListener.unsubscribePrice();
  }


  /**
   * Subscribes to trade updates
   */
  public Observable<STradeRecord> createTradesStream() {
    log.trace("createTradesStream()");

    sendCommand(
        TradeRecordsSubscribe.builder()
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createTradesStream();
  }


  /**
   * Unsubscribes trade updates
   */
  public void unsubscribeTrades() {
    log.trace("unsubscribeTrades()");

    sendCommand(
        TradeRecordsStop.builder()
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeTrades();
  }


  /**
   * Subscribes to news updates
   */
  public Observable<SNewsRecord> createNewsStream() {
    log.trace("createNewsStream()");

    sendCommand(NewsSubscribe.builder().streamSessionId(this.sessionId).build().toJSONString());

    return this.streamWebSocketListener.createNewsStream();
  }


  /**
   * Unsubscribes news updates
   */
  public void unsubscribeNews() {
    log.trace("unsubscribeNews()");

    sendCommand(
        NewsStop.builder()
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeNews();
  }


  /**
   * Subscribes to profits updates
   */
  public Observable<SProfitRecord> createProfitsStream() {
    log.trace("createProfitsStream()");

    sendCommand(
        ProfitsSubscribe.builder()
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.subscribeProfits();
  }


  /**
   * Unsubscribes profits updates
   */
  public void unsubscribeProfits() {
    log.trace("unsubscribeProfits()");

    sendCommand(
        ProfitsStop.builder()
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeProfits();
  }


  /**
   * Subscribes to balance updates
   */
  public Observable<SBalanceRecord> createBalanceStream() {
    log.trace("createBalanceStream()");

    sendCommand(
        BalanceSubscribe.builder()
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createBalanceStream();
  }


  /**
   * Unsubscribes balance updates
   */
  public void unsubscribeBalance() {
    log.trace("unsubscribeBalance()");

    sendCommand(BalanceStop.builder()
        .build()
        .toJSONString());

    this.streamWebSocketListener.unsubscribeBalance();
  }


  public Observable<SCandleRecord> createCandleStream() {
    return createCandleStream(this.symbol);
  }


  /**
   * Subscribes for chart candles of a chosen symbol.
   * <p>
   * Stream may receive candles from another subscribed assets.
   *
   * @see #subscribeCandle(String)
   */
  public Observable<SCandleRecord> createCandleStream(final String symbol) {
    log.trace("createCandleStream(symbol={})", symbol);

    if (symbol != null) {
      subscribeCandle(symbol);
    }

    return this.streamWebSocketListener.createCandleStream();
  }


  /**
   * Requests streamed data for symbol.
   *
   * @param symbol to receive
   */
  public void subscribeCandle(final String symbol) {
    log.trace("subscribeCandleStream(symbol={})", symbol);

    sendCommand(CandlesSubscribe.builder()
        .symbol(symbol)
        .streamSessionId(this.sessionId)
        .build()
        .toJSONString());
  }


  public void unsubscribeCandle() {
    unsubscribeCandle(this.symbol);
  }


  /**
   * Unsubscribes chart candles of a symbol
   */
  public void unsubscribeCandle(final String symbol) {
    log.trace("unsubscribeCandle(symbol={})", symbol);

    sendCommand(CandlesStop.builder()
        .symbol(symbol)
        .build()
        .toJSONString());

    this.streamWebSocketListener.unsubscribeCandle();
  }


  /**
   * Subscribes to keep alive messages
   */
  public Observable<SKeepAliveRecord> createKeepAliveStream() {
    log.trace("createKeepAliveStream()");

    sendCommand(KeepAliveSubscribe.builder()
        .streamSessionId(this.sessionId)
        .build()
        .toJSONString());

    return this.streamWebSocketListener.createKeepAliveStream();
  }


  /**
   * Unsubscribes keep alive messages
   */
  public void unsubscribeKeepAlive() {
    log.trace("unsubscribeKeepAlive()");

    sendCommand(KeepAliveStop.builder()
        .build()
        .toJSONString());

    this.streamWebSocketListener.unsubscribeKeepAlive();
  }


  /**
   * Subscribes to trade status updates
   */
  public Observable<STradeStatusRecord> createTradesStatusStream() {
    log.trace("createTradesStatusStream()");

    sendCommand(TradeStatusRecordsSubscribe.builder()
        .streamSessionId(this.sessionId)
        .build()
        .toJSONString());

    return this.streamWebSocketListener.createTradesStatusStream();
  }


  /**
   * Unsubscribes trade status updates
   */
  public void unsubscribeTradeStatus() {
    log.trace("unsubscribeTradeStatus()");

    sendCommand(TradeStatusRecordsStop.builder()
        .build()
        .toJSONString());

    this.streamWebSocketListener.unsubscribeTradeStatus();
  }


  public void disconnect() {
    log.trace("disconnect()");

    this.syncWebsocket.close(1000, "AppShutdown");
    this.streamWebsocket.close(1000, "AppShutdown");
  }
}
