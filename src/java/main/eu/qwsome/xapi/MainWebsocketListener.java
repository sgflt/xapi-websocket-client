package eu.qwsome.xapi;

import java.util.concurrent.LinkedBlockingDeque;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.SingleSubject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import eu.qwsome.xapi.stream.response.AllSymbolsResponse;
import eu.qwsome.xapi.stream.response.LoginResponse;
import eu.qwsome.xapi.stream.response.ResponseParser;
import eu.qwsome.xapi.stream.response.SymbolResponse;
import eu.qwsome.xapi.stream.response.TradeTransactionResponse;
import eu.qwsome.xapi.stream.response.TradesResponse;

/**
 * Allows only one command to be executed at the same time.
 *
 * @author Lukáš Kvídera
 */
@Slf4j
public class MainWebsocketListener extends WebSocketListener {

  private final SingleSubject<LoginResponse> loginSubject = SingleSubject.create();
  private final SingleSubject<TradeTransactionResponse> tradeTransactionSubject = SingleSubject.create();
  private final SingleSubject<AllSymbolsResponse> allSymbolsSubject = SingleSubject.create();
  private final SingleSubject<SymbolResponse> getSymbolSubject = SingleSubject.create();
  private final SingleSubject<TradesResponse> getTradesSubject = SingleSubject.create();

  private final LinkedBlockingDeque<String> command = new LinkedBlockingDeque<>(1);


  @Override
  public void onFailure(
      @NotNull final WebSocket webSocket,
      @NotNull final Throwable t,
      @Nullable final Response response
  ) {
    this.loginSubject.onError(t);
    this.allSymbolsSubject.onError(t);
    this.tradeTransactionSubject.onError(t);
    this.getSymbolSubject.onError(t);
    this.getTradesSubject.onError(t);
  }


  @Override
  public synchronized void onMessage(@NotNull final WebSocket webSocket, @NotNull final String text) {
    log.debug("command {} | onMessage {}", this.command, text);

    try {
      final var lastCommand = this.command.pop();
      if ("login".equals(lastCommand)) {
        this.loginSubject.onSuccess(new ResponseParser().parseLogin(text));
      } else if ("buy".equals(lastCommand) || "closeBuy".equals(lastCommand)) {
        this.tradeTransactionSubject.onSuccess(new ResponseParser().parseTradeTransaction(text));
      } else if ("allSymbols".equals(lastCommand)) {
        this.allSymbolsSubject.onSuccess(new ResponseParser().parseAllSymbols(text));
      } else if ("getSymbol".equals(lastCommand)) {
        this.getSymbolSubject.onSuccess(new ResponseParser().parseGetSymbol(text));
      } else if ("getTrades".equals(lastCommand)) {
        this.getTradesSubject.onSuccess((new ResponseParser().parseGetTrades(text)));
      }
    } catch (final Exception e) {
      onFailure(webSocket, e, null);
    }
  }


  public void setCommand(final String command) {
    try {
      this.command.put(command);
    } catch (final InterruptedException e) {
      throw new RuntimeException(e);
    }
  }


  public Single<LoginResponse> createLoginStream() {
    return this.loginSubject.subscribeOn(Schedulers.io());
  }


  public Single<TradeTransactionResponse> createTradeTransactionStream() {
    return this.tradeTransactionSubject.subscribeOn(Schedulers.io());
  }


  public Single<AllSymbolsResponse> createAllSymbolsStream() {
    return this.allSymbolsSubject.subscribeOn(Schedulers.io());
  }


  public Single<SymbolResponse> createGetSymbolStream() {
    return this.getSymbolSubject.subscribeOn(Schedulers.io());
  }


  public Single<TradesResponse> createGetTradesStream() {
    return this.getTradesSubject.subscribeOn(Schedulers.io());
  }
}
