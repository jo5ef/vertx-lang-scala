/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.scala.core.http;

import io.vertx.scala.core.metrics.Measured
import scala.util.Try
import io.vertx.core.Handler

/**
  * An HTTP and WebSockets server.
  * 
  * You receive HTTP requests by providing a [[io.vertx.scala.core.http.HttpServer#requestHandler]]. As requests arrive on the server the handler
  * will be called with the requests.
  * 
  * You receive WebSockets by providing a [[io.vertx.scala.core.http.HttpServer#websocketHandler]]. As WebSocket connections arrive on the server, the
  * WebSocket is passed to the handler.
  */
class HttpServer(private val _asJava: io.vertx.core.http.HttpServer) 
    extends io.vertx.scala.core.metrics.Measured {

  def asJava: java.lang.Object = _asJava

  /**
    * Whether the metrics are enabled for this measured object
    * @return true if the metrics are enabled
    */
  def isMetricsEnabled(): Boolean = {
    _asJava.isMetricsEnabled()
  }

  /**
    * Return the request stream for the server. As HTTP requests are received by the server,
    * instances of [[io.vertx.scala.core.http.HttpServerRequest]] will be created and passed to the stream [[io.vertx.scala.core.streams.ReadStream#handler]].
    * @return the request stream
    */
  def requestStream(): io.vertx.scala.core.http.HttpServerRequestStream = {
    HttpServerRequestStream.apply(_asJava.requestStream())
  }

  /**
    * Set the request handler for the server to `requestHandler`. As HTTP requests are received by the server,
    * instances of [[io.vertx.scala.core.http.HttpServerRequest]] will be created and passed to this handler.
    * @return a reference to this, so the API can be used fluently
    */
  def requestHandler(handler: io.vertx.scala.core.http.HttpServerRequest => Unit): io.vertx.scala.core.http.HttpServer = {
    import io.vertx.lang.scala.HandlerOps._
    import scala.collection.JavaConverters._
    HttpServer.apply(_asJava.requestHandler(funcToMappedHandler(HttpServerRequest.apply)(handler)))
  }

  /**
    * Return the websocket stream for the server. If a websocket connect handshake is successful a
    * new [[io.vertx.scala.core.http.ServerWebSocket]] instance will be created and passed to the stream [[io.vertx.scala.core.streams.ReadStream#handler]].
    * @return the websocket stream
    */
  def websocketStream(): io.vertx.scala.core.http.ServerWebSocketStream = {
    ServerWebSocketStream.apply(_asJava.websocketStream())
  }

  /**
    * Set the websocket handler for the server to `wsHandler`. If a websocket connect handshake is successful a
    * new [[io.vertx.scala.core.http.ServerWebSocket]] instance will be created and passed to the handler.
    * @return a reference to this, so the API can be used fluently
    */
  def websocketHandler(handler: io.vertx.scala.core.http.ServerWebSocket => Unit): io.vertx.scala.core.http.HttpServer = {
    import io.vertx.lang.scala.HandlerOps._
    import scala.collection.JavaConverters._
    HttpServer.apply(_asJava.websocketHandler(funcToMappedHandler(ServerWebSocket.apply)(handler)))
  }

  /**
    * Tell the server to start listening. The server will listen on the port and host specified in the
    * <a href="../../../../../../../cheatsheet/HttpServerOptions.html">HttpServerOptions</a> that was used when creating the server.
    * 
    * The listen happens asynchronously and the server may not be listening until some time after the call has returned.
    * @return a reference to this, so the API can be used fluently
    */
  def listen(): io.vertx.scala.core.http.HttpServer = {
    _asJava.listen()
    this
  }

  /**
    * Tell the server to start listening. The server will listen on the port and host specified here,
    * ignoring any value set in the <a href="../../../../../../../cheatsheet/HttpServerOptions.html">HttpServerOptions</a> that was used when creating the server.
    * 
    * The listen happens asynchronously and the server may not be listening until some time after the call has returned.
    * @param port the port to listen on
    * @param host the host to listen on
    * @return a reference to this, so the API can be used fluently
    */
  def listen(port: Int, host: String): io.vertx.scala.core.http.HttpServer = {
    _asJava.listen(port, host)
    this
  }

  /**
    * Like [[io.vertx.scala.core.http.HttpServer#listen]] but supplying a handler that will be called when the server is actually
    * listening (or has failed).
    * @param port the port to listen on
    * @param host the host to listen on
    * @param listenHandler the listen handler
    */
  def listen(port: Int, host: String)(listenHandler: Try[io.vertx.scala.core.http.HttpServer] => Unit): io.vertx.scala.core.http.HttpServer = {
    import io.vertx.lang.scala.HandlerOps._
    import scala.collection.JavaConverters._
    _asJava.listen(port, host, funcToMappedAsyncResultHandler(HttpServer.apply)(listenHandler))
    this
  }

  /**
    * Like [[io.vertx.scala.core.http.HttpServer#listen]] but the server will listen on host "0.0.0.0" and port specified here ignoring
    * any value in the <a href="../../../../../../../cheatsheet/HttpServerOptions.html">HttpServerOptions</a> that was used when creating the server.
    * @param port the port to listen on
    * @return a reference to this, so the API can be used fluently
    */
  def listen(port: Int): io.vertx.scala.core.http.HttpServer = {
    _asJava.listen(port)
    this
  }

  /**
    * Like [[io.vertx.scala.core.http.HttpServer#listen]] but supplying a handler that will be called when the server is actually listening (or has failed).
    * @param port the port to listen on
    * @param listenHandler the listen handler
    */
  def listen(port: Int)(listenHandler: Try[io.vertx.scala.core.http.HttpServer] => Unit): io.vertx.scala.core.http.HttpServer = {
    import io.vertx.lang.scala.HandlerOps._
    import scala.collection.JavaConverters._
    _asJava.listen(port, funcToMappedAsyncResultHandler(HttpServer.apply)(listenHandler))
    this
  }

  /**
    * Like [[io.vertx.scala.core.http.HttpServer#listen]] but supplying a handler that will be called when the server is actually listening (or has failed).
    * @param listenHandler the listen handler
    */
  def listen(listenHandler: Try[io.vertx.scala.core.http.HttpServer] => Unit): io.vertx.scala.core.http.HttpServer = {
    import io.vertx.lang.scala.HandlerOps._
    import scala.collection.JavaConverters._
    _asJava.listen(funcToMappedAsyncResultHandler(HttpServer.apply)(listenHandler))
    this
  }

  /**
    * Close the server. Any open HTTP connections will be closed.
    * 
    * The close happens asynchronously and the server may not be closed until some time after the call has returned.
    */
  def close(): Unit = {
    _asJava.close()
  }

  /**
    * Like [[io.vertx.scala.core.http.HttpServer#close]] but supplying a handler that will be called when the server is actually closed (or has failed).
    * @param completionHandler the handler
    */
  def close(completionHandler: Try[Unit] => Unit): Unit = {
    import io.vertx.lang.scala.HandlerOps._
    import scala.collection.JavaConverters._
    _asJava.close(funcToMappedAsyncResultHandler[java.lang.Void, Unit](x => x.asInstanceOf[Unit])(completionHandler))
  }

}

object HttpServer {

  def apply(_asJava: io.vertx.core.http.HttpServer): io.vertx.scala.core.http.HttpServer =
    new io.vertx.scala.core.http.HttpServer(_asJava)
}