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
import eu.qwsome.xapi.stream.response.ChartResponse;
import eu.qwsome.xapi.stream.response.LoginResponse;
import eu.qwsome.xapi.stream.response.ResponseParser;
import eu.qwsome.xapi.stream.response.SymbolResponse;
import eu.qwsome.xapi.stream.response.TradeTransactionResponse;
import eu.qwsome.xapi.stream.response.TradeTransactionStatusResponse;
import eu.qwsome.xapi.stream.response.TradesResponse;

/**
 * Allows only one command to be executed at the same time.
 *
 * @author Lukáš Kvídera
 */
@Slf4j
public class MainWebsocketListener extends WebSocketListener {

  private SingleSubject<LoginResponse> loginSubject = SingleSubject.create();
  private SingleSubject<TradeTransactionResponse> tradeTransactionSubject = SingleSubject.create();
  private SingleSubject<TradeTransactionStatusResponse> tradeTransactionStatusSubject = SingleSubject.create();
  private SingleSubject<AllSymbolsResponse> allSymbolsSubject = SingleSubject.create();
  private SingleSubject<SymbolResponse> getSymbolSubject = SingleSubject.create();
  private SingleSubject<TradesResponse> getTradesSubject = SingleSubject.create();
  private SingleSubject<ChartResponse> getChartLastRequestSubject = SingleSubject.create();

  private final LinkedBlockingDeque<String> command = new LinkedBlockingDeque<>(1);


  @Override
  public void onFailure(
      @NotNull final WebSocket webSocket,
      @NotNull final Throwable t,
      @Nullable final Response response
  ) {
    this.loginSubject.onError(t);
    this.tradeTransactionSubject.onError(t);
    this.tradeTransactionStatusSubject.onError(t);
    this.allSymbolsSubject.onError(t);
    this.getSymbolSubject.onError(t);
    this.getTradesSubject.onError(t);
    this.getChartLastRequestSubject.onError(t);
  }


  @Override
  public synchronized void onMessage(@NotNull final WebSocket webSocket, @NotNull final String text) {
    log.debug("command {} | onMessage {}", this.command, text);

    try {
      final var lastCommand = this.command.pop();
      if ("login".equals(lastCommand)) {
        this.loginSubject.onSuccess(new ResponseParser().parseLogin(text));
      } else if (
          "buy".equals(lastCommand)
          || "sell".equals(lastCommand)
          || "closeBuy".equals(lastCommand)
          || "closeSell".equals(lastCommand)
      ) {
        this.tradeTransactionSubject.onSuccess(new ResponseParser().parseTradeTransaction(text));
      } else if ("allSymbols".equals(lastCommand)) {
        this.allSymbolsSubject.onSuccess(new ResponseParser().parseAllSymbols(text));
      } else if ("getSymbol".equals(lastCommand)) {
        this.getSymbolSubject.onSuccess(new ResponseParser().parseGetSymbol(text));
      } else if ("getTrades".equals(lastCommand)) {
        this.getTradesSubject.onSuccess((new ResponseParser().parseGetTrades(text)));
      } else if ("transactionStatus".equals(lastCommand)) {
        this.tradeTransactionStatusSubject.onSuccess(new ResponseParser().parseTradeTransactionStatus(text));
      } else if ("getChartLastRequest".equals(lastCommand)) {
        this.getChartLastRequestSubject.onSuccess(new ResponseParser().parseGetChartLastRequest(text));
      }
    } catch (final Exception e) {
      onFailure(webSocket, e, null);
    }
  }


  public void setCommand(final String command) {
    log.trace("setCommand {}", command);

    try {
      this.command.put(command);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }
  }


  public Single<LoginResponse> createLoginStream() {
    this.loginSubject = SingleSubject.create();
    return this.loginSubject.observeOn(Schedulers.io());
  }


  public Single<TradeTransactionResponse> createTradeTransactionStream() {
    this.tradeTransactionSubject = SingleSubject.create();
    return this.tradeTransactionSubject.observeOn(Schedulers.io());
  }


  public Single<TradeTransactionStatusResponse> createTradeTransactionStatusStream() {
    this.tradeTransactionStatusSubject = SingleSubject.create();
    return this.tradeTransactionStatusSubject.observeOn(Schedulers.io());
  }


  public Single<AllSymbolsResponse> createAllSymbolsStream() {
    this.allSymbolsSubject = SingleSubject.create();
    return this.allSymbolsSubject.observeOn(Schedulers.io());
  }


  public Single<SymbolResponse> createGetSymbolStream() {
    this.getSymbolSubject = SingleSubject.create();
    return this.getSymbolSubject.observeOn(Schedulers.io());
  }


  public Single<TradesResponse> createGetTradesStream() {
    this.getTradesSubject = SingleSubject.create();
    return this.getTradesSubject.observeOn(Schedulers.io());
  }


  public Single<ChartResponse> createGetChartLastRequestStream() {
    this.getChartLastRequestSubject = SingleSubject.create();
    return this.getChartLastRequestSubject.observeOn(Schedulers.io());
  }
}
