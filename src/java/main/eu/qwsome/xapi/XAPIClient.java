package eu.qwsome.xapi;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

import eu.qwsome.xapi.stream.records.request.TradeTransInfoRecord;
import eu.qwsome.xapi.stream.records.response.SBalanceRecord;
import eu.qwsome.xapi.stream.records.response.SCandleRecord;
import eu.qwsome.xapi.stream.records.response.SKeepAliveRecord;
import eu.qwsome.xapi.stream.records.response.SNewsRecord;
import eu.qwsome.xapi.stream.records.response.SProfitRecord;
import eu.qwsome.xapi.stream.records.response.STickRecord;
import eu.qwsome.xapi.stream.records.response.STradeRecord;
import eu.qwsome.xapi.stream.records.response.STradeStatusRecord;
import eu.qwsome.xapi.stream.response.AllSymbolsResponse;
import eu.qwsome.xapi.stream.response.LoginResponse;
import eu.qwsome.xapi.stream.response.SymbolResponse;
import eu.qwsome.xapi.stream.response.TradeTransactionResponse;
import eu.qwsome.xapi.stream.response.TradesResponse;
import eu.qwsome.xapi.stream.subscription.BalanceStop;
import eu.qwsome.xapi.stream.subscription.BalanceSubscribe;
import eu.qwsome.xapi.stream.subscription.CandlesStop;
import eu.qwsome.xapi.stream.subscription.CandlesSubscribe;
import eu.qwsome.xapi.stream.subscription.KeepAliveStop;
import eu.qwsome.xapi.stream.subscription.KeepAliveSubscribe;
import eu.qwsome.xapi.stream.subscription.NewsStop;
import eu.qwsome.xapi.stream.subscription.NewsSubscribe;
import eu.qwsome.xapi.stream.subscription.ProfitsStop;
import eu.qwsome.xapi.stream.subscription.ProfitsSubscribe;
import eu.qwsome.xapi.stream.subscription.StreamPingCommand;
import eu.qwsome.xapi.stream.subscription.TickPricesStop;
import eu.qwsome.xapi.stream.subscription.TickPricesSubscribe;
import eu.qwsome.xapi.stream.subscription.TradeRecordsStop;
import eu.qwsome.xapi.stream.subscription.TradeRecordsSubscribe;
import eu.qwsome.xapi.stream.subscription.TradeStatusRecordsStop;
import eu.qwsome.xapi.stream.subscription.TradeStatusRecordsSubscribe;
import eu.qwsome.xapi.sync.Credentials;
import eu.qwsome.xapi.sync.command.AllSymbolsCommand;
import eu.qwsome.xapi.sync.command.LoginCommand;
import eu.qwsome.xapi.sync.command.SymbolCommand;
import eu.qwsome.xapi.sync.command.TradeTransactionCommand;
import eu.qwsome.xapi.sync.command.TradesCommand;

@Slf4j
public class XAPIClient {

  private final OkHttpClient client = new OkHttpClient.Builder()
      .pingInterval(Duration.ofSeconds(30))
      .build();
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


  private void keepAlive(final Long minute) {
    log.trace("keepAlive(minute={})", minute);

    this.streamWebsocket.send(StreamPingCommand.builder().streamSessionId(this.sessionId).build().toJSONString());
  }


  private @NonNull Single<LoginResponse> login(final Credentials credentials) {
    log.trace("login(credentials={})", credentials);

    return this.syncListener.createLoginStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand("login");
          this.syncWebsocket.send(new LoginCommand(credentials).toJSONString());
        }).doOnSuccess(loginResponse -> {
          connectStream(loginResponse.getStreamSessionId());

          Observable.timer(10, TimeUnit.MINUTES)
              .subscribeOn(Schedulers.io())
              .repeat()
              .subscribe(this::keepAlive);
        })
        ;
  }


  private void connectStream(final String sessionId) {
    log.trace("connectStream(sessionId={})", sessionId);

    this.sessionId = sessionId;
    final var request = new Request.Builder()
        .url(this.url.concat("Stream"))
        .build();
    this.streamWebsocket = this.client.newWebSocket(request, this.streamWebSocketListener);
  }


  public AllSymbolsResponse getAllSymbols() {
    log.trace("getAllSymbols()");

    return this.syncListener.createAllSymbolsStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand("allSymbols");
          this.syncWebsocket.send(new AllSymbolsCommand().toJSONString());
        })
        .blockingGet();
  }


  public SymbolResponse getSymbol() {
    log.trace("getSymbol()");

    return this.syncListener.createGetSymbolStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand("getSymbol");
          this.syncWebsocket.send(new SymbolCommand(this.symbol).toJSONString());
        }).blockingGet();
  }


  public TradesResponse getTrades() {
    log.trace("getTrades()");

    return this.syncListener.createGetTradesStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand("getTrades");
          this.syncWebsocket.send(new TradesCommand(true).toJSONString());
        }).blockingGet();
  }


  public TradeTransactionResponse operatePosition(final TradeTransInfoRecord tradeTransInfoRecord) {
    log.debug("operatePosition(tradeTransInfoRecord={})", tradeTransInfoRecord);

    return this.syncListener.createTradeTransactionStream()
        .doOnSubscribe(disposable -> {
          this.syncListener.setCommand("buy");
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


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds
   */
  public Observable<STickRecord> createPriceStream() {
    log.trace("createPriceStream()");

    this.streamWebsocket.send(
        TickPricesSubscribe.builder()
            .symbol(this.symbol)
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createPriceStream();
  }


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds
   */
  public Observable<STickRecord> createPriceStream(final int minArrivalTime) {
    log.trace("createPriceStream(minArrivalTime={})", minArrivalTime);

    this.streamWebsocket.send(
        TickPricesSubscribe.builder()
            .symbol(this.symbol)
            .streamSessionId(this.sessionId)
            .minArrivalTime(minArrivalTime)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createPriceStream();
  }


  /**
   * Subscribes for prices of a chosen symbol with the given minimum arrival time in milliseconds and the given maxLevel
   */
  public Observable<STickRecord> createPriceStream(
      final int minArrivalTime,
      final int maxLevel
  ) {
    log.trace("createPriceStream(minArrivalTime={}, maxLevel={})", minArrivalTime, maxLevel);

    this.streamWebsocket.send(
        TickPricesSubscribe.builder()
            .symbol(this.symbol)
            .streamSessionId(this.sessionId)
            .minArrivalTime(minArrivalTime)
            .maxLevel(maxLevel)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createPriceStream();
  }


  /**
   * Subscribes for prices of a chosen symbol with the given maxLevel
   */
  public Observable<STickRecord> createPriceStreamMaxLevel(
      final int maxLevel
  ) {
    log.trace("createPriceStreamMaxLevel(maxLevel={})", maxLevel);

    this.streamWebsocket.send(
        TickPricesSubscribe.builder()
            .symbol(this.symbol)
            .streamSessionId(this.sessionId)
            .maxLevel(maxLevel)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createPriceStream();
  }


  /**
   * Unsubscribes prices of a symbol
   */
  public void unsubscribePrice() {
    log.trace("unsubscribePrice()");

    this.streamWebsocket.send(
        TickPricesStop.builder()
            .symbol(this.symbol)
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribePrice();
  }


  /**
   * Subscribes to trade updates
   */
  public Observable<STradeRecord> createTradesStream() {
    log.trace("createTradesStream()");

    this.streamWebsocket.send(
        TradeRecordsSubscribe.builder()
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString())
    ;

    return this.streamWebSocketListener.createTradesStream();
  }


  /**
   * Unsubscribes trade updates
   */
  public void unsubscribeTrades() {
    log.trace("unsubscribeTrades()");

    this.streamWebsocket.send(
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

    this.streamWebsocket.send(NewsSubscribe.builder().streamSessionId(this.sessionId).build().toJSONString());

    return this.streamWebSocketListener.createNewsStream();
  }


  /**
   * Unsubscribes news updates
   */
  public void unsubscribeNews() {
    log.trace("unsubscribeNews()");

    this.streamWebsocket.send(
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

    this.streamWebsocket.send(
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

    this.streamWebsocket.send(
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

    this.streamWebsocket.send(
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

    this.streamWebsocket.send(
        BalanceStop.builder()
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeBalance();
  }


  /**
   * Subscribes for chart candles of a chosen symbol
   */
  public Observable<SCandleRecord> createCandleStream() {
    log.trace("createCandleStream()");

    this.streamWebsocket.send(
        CandlesSubscribe.builder()
            .symbol(this.symbol)
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createCandleStream();
  }


  /**
   * Unsubscribes chart candles of a symbol
   */
  public void unsubscribeCandle() {
    log.trace("unsubscribeCandle()");

    this.streamWebsocket.send(
        CandlesStop.builder()
            .symbol(this.symbol)
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeCandle();
  }


  /**
   * Subscribes to keep alive messages
   */
  public Observable<SKeepAliveRecord> createKeepAliveStream() {
    log.trace("createKeepAliveStream()");

    this.streamWebsocket.send(
        KeepAliveSubscribe.builder()
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createKeepAliveStream();
  }


  /**
   * Unsubscribes keep alive messages
   */
  public void unsubscribeKeepAlive() {
    log.trace("unsubscribeKeepAlive()");

    this.streamWebsocket.send(
        KeepAliveStop.builder()
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeKeepAlive();
  }


  /**
   * Subscribes to trade status updates
   */
  public Observable<STradeStatusRecord> createTradesStatusStream() {
    log.trace("createTradesStatusStream()");

    this.streamWebsocket.send(
        TradeStatusRecordsSubscribe.builder()
            .streamSessionId(this.sessionId)
            .build()
            .toJSONString()
    );

    return this.streamWebSocketListener.createTradesStatusStream();
  }


  /**
   * Unsubscribes trade status updates
   */
  public void unsubscribeTradeStatus() {
    log.trace("unsubscribeTradeStatus()");

    this.streamWebsocket.send(
        TradeStatusRecordsStop.builder()
            .build()
            .toJSONString()
    );

    this.streamWebSocketListener.unsubscribeTradeStatus();
  }


  public void disconnect() {
    log.trace("disconnect()");

    this.syncWebsocket.close(1000, "AppShutdown");
    this.streamWebsocket.close(1000, "AppShutdown");
  }
}
