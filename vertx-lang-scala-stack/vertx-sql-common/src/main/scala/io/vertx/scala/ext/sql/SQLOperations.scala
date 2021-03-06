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

package io.vertx.scala.ext.sql

import io.vertx.lang.scala.HandlerOps._
import scala.reflect.runtime.universe._
import io.vertx.lang.scala.Converter._
import io.vertx.lang.scala.AsyncResultWrapper
import io.vertx.core.json.JsonArray
import io.vertx.ext.sql.{SQLOperations => JSQLOperations}
import io.vertx.ext.sql.{ResultSet => JResultSet}
import io.vertx.ext.sql.{UpdateResult => JUpdateResult}
import io.vertx.ext.sql.{SQLRowStream => JSQLRowStream}
import io.vertx.core.AsyncResult
import io.vertx.core.Handler

/**
  * Represents a SQL query interface to a database
  */
trait SQLOperations {

  def asJava: java.lang.Object

  /**
    * Executes the given SQL <code>SELECT</code> statement which returns the results of the query.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def query(sql: String, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations

  /**
    * Executes the given SQL <code>SELECT</code> prepared statement which returns the results of the query.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param params these are the parameters to fill the statement.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def queryWithParams(sql: String, params: io.vertx.core.json.JsonArray, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations

  /**
    * Executes the given SQL <code>SELECT</code> statement which returns the results of the query as a read stream.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param handler the handler which is called once the operation completes. It will return a `SQLRowStream`.
    */
  def queryStream(sql: String, handler: Handler[AsyncResult[SQLRowStream]]): SQLOperations

  /**
    * Executes the given SQL <code>SELECT</code> statement which returns the results of the query as a read stream.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param params these are the parameters to fill the statement.
    * @param handler the handler which is called once the operation completes. It will return a `SQLRowStream`.
    */
  def queryStreamWithParams(sql: String, params: io.vertx.core.json.JsonArray, handler: Handler[AsyncResult[SQLRowStream]]): SQLOperations

  /**
    * Execute a one shot SQL statement that returns a single SQL row. This method will reduce the boilerplate code by
    * getting a connection from the pool (this object) and return it back after the execution. Only the first result
    * from the result set is returned.
    * @param sql the statement to execute
    * @param handler the result handler
    * @return self
    */
  def querySingle(sql: String, handler: Handler[AsyncResult[io.vertx.core.json.JsonArray]]): SQLOperations

  /**
    * Execute a one shot SQL statement with arguments that returns a single SQL row. This method will reduce the
    * boilerplate code by getting a connection from the pool (this object) and return it back after the execution.
    * Only the first result from the result set is returned.
    * @param sql the statement to execute
    * @param arguments the arguments
    * @param handler the result handler
    * @return self
    */
  def querySingleWithParams(sql: String, arguments: io.vertx.core.json.JsonArray, handler: Handler[AsyncResult[io.vertx.core.json.JsonArray]]): SQLOperations

  /**
    * Executes the given SQL statement which may be an <code>INSERT</code>, <code>UPDATE</code>, or <code>DELETE</code>
    * statement.
    * @param sql the SQL to execute. For example <code>INSERT INTO table ...</code>
    * @param resultHandler the handler which is called once the operation completes.
    */
  def update(sql: String, resultHandler: Handler[AsyncResult[UpdateResult]]): SQLOperations

  /**
    * Executes the given prepared statement which may be an <code>INSERT</code>, <code>UPDATE</code>, or <code>DELETE</code>
    * statement with the given parameters
    * @param sql the SQL to execute. For example <code>INSERT INTO table ...</code>
    * @param params these are the parameters to fill the statement.
    * @param resultHandler the handler which is called once the operation completes.
    */
  def updateWithParams(sql: String, params: io.vertx.core.json.JsonArray, resultHandler: Handler[AsyncResult[UpdateResult]]): SQLOperations

  /**
    * Calls the given SQL <code>PROCEDURE</code> which returns the result from the procedure.
    * @param sql the SQL to execute. For example <code>{call getEmpName`</code>.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def call(sql: String, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations

  /**
    * Calls the given SQL <code>PROCEDURE</code> which returns the result from the procedure.
    *
    * The index of params and outputs are important for both arrays, for example when dealing with a prodecure that
    * takes the first 2 arguments as input values and the 3 arg as an output then the arrays should be like:
    *
    * <pre>
    *   params = [VALUE1, VALUE2, null]
    *   outputs = [null, null, "VARCHAR"]
    * </pre>
    * @param sql the SQL to execute. For example <code>{call getEmpName (?, ?)`</code>.
    * @param params these are the parameters to fill the statement.
    * @param outputs these are the outputs to fill the statement.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def callWithParams(sql: String, params: io.vertx.core.json.JsonArray, outputs: io.vertx.core.json.JsonArray, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations

 /**
   * Like [[query]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryFuture(sql: String): scala.concurrent.Future[ResultSet]
 /**
   * Like [[queryWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray): scala.concurrent.Future[ResultSet]
 /**
   * Like [[queryStream]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryStreamFuture(sql: String): scala.concurrent.Future[SQLRowStream]
 /**
   * Like [[queryStreamWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryStreamWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray): scala.concurrent.Future[SQLRowStream]
 /**
   * Like [[querySingle]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def querySingleFuture(sql: String): scala.concurrent.Future[io.vertx.core.json.JsonArray]
 /**
   * Like [[querySingleWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def querySingleWithParamsFuture(sql: String, arguments: io.vertx.core.json.JsonArray): scala.concurrent.Future[io.vertx.core.json.JsonArray]
 /**
   * Like [[update]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def updateFuture(sql: String): scala.concurrent.Future[UpdateResult]
 /**
   * Like [[updateWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def updateWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray): scala.concurrent.Future[UpdateResult]
 /**
   * Like [[call]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def callFuture(sql: String): scala.concurrent.Future[ResultSet]
 /**
   * Like [[callWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def callWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray, outputs: io.vertx.core.json.JsonArray): scala.concurrent.Future[ResultSet]
}

object SQLOperations {
  def apply(asJava: JSQLOperations): SQLOperations = new SQLOperationsImpl(asJava)
    private class SQLOperationsImpl(private val _asJava: Object) extends SQLOperations {

      def asJava = _asJava


  /**
    * Executes the given SQL <code>SELECT</code> statement which returns the results of the query.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def query(sql: String, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].query(sql.asInstanceOf[java.lang.String], {x: AsyncResult[JResultSet] => resultHandler.handle(AsyncResultWrapper[JResultSet, ResultSet](x, a => ResultSet(a)))})
    this
  }

  /**
    * Executes the given SQL <code>SELECT</code> prepared statement which returns the results of the query.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param params these are the parameters to fill the statement.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def queryWithParams(sql: String, params: io.vertx.core.json.JsonArray, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].queryWithParams(sql.asInstanceOf[java.lang.String], params, {x: AsyncResult[JResultSet] => resultHandler.handle(AsyncResultWrapper[JResultSet, ResultSet](x, a => ResultSet(a)))})
    this
  }

  /**
    * Executes the given SQL <code>SELECT</code> statement which returns the results of the query as a read stream.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param handler the handler which is called once the operation completes. It will return a `SQLRowStream`.
    */
  def queryStream(sql: String, handler: Handler[AsyncResult[SQLRowStream]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].queryStream(sql.asInstanceOf[java.lang.String], {x: AsyncResult[JSQLRowStream] => handler.handle(AsyncResultWrapper[JSQLRowStream, SQLRowStream](x, a => SQLRowStream(a)))})
    this
  }

  /**
    * Executes the given SQL <code>SELECT</code> statement which returns the results of the query as a read stream.
    * @param sql the SQL to execute. For example <code>SELECT * FROM table ...</code>.
    * @param params these are the parameters to fill the statement.
    * @param handler the handler which is called once the operation completes. It will return a `SQLRowStream`.
    */
  def queryStreamWithParams(sql: String, params: io.vertx.core.json.JsonArray, handler: Handler[AsyncResult[SQLRowStream]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].queryStreamWithParams(sql.asInstanceOf[java.lang.String], params, {x: AsyncResult[JSQLRowStream] => handler.handle(AsyncResultWrapper[JSQLRowStream, SQLRowStream](x, a => SQLRowStream(a)))})
    this
  }

  /**
    * Execute a one shot SQL statement that returns a single SQL row. This method will reduce the boilerplate code by
    * getting a connection from the pool (this object) and return it back after the execution. Only the first result
    * from the result set is returned.
    * @param sql the statement to execute
    * @param handler the result handler
    * @return self
    */
  def querySingle(sql: String, handler: Handler[AsyncResult[io.vertx.core.json.JsonArray]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].querySingle(sql.asInstanceOf[java.lang.String], {x: AsyncResult[JsonArray] => handler.handle(AsyncResultWrapper[JsonArray, io.vertx.core.json.JsonArray](x, a => a))})
    this
  }

  /**
    * Execute a one shot SQL statement with arguments that returns a single SQL row. This method will reduce the
    * boilerplate code by getting a connection from the pool (this object) and return it back after the execution.
    * Only the first result from the result set is returned.
    * @param sql the statement to execute
    * @param arguments the arguments
    * @param handler the result handler
    * @return self
    */
  def querySingleWithParams(sql: String, arguments: io.vertx.core.json.JsonArray, handler: Handler[AsyncResult[io.vertx.core.json.JsonArray]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].querySingleWithParams(sql.asInstanceOf[java.lang.String], arguments, {x: AsyncResult[JsonArray] => handler.handle(AsyncResultWrapper[JsonArray, io.vertx.core.json.JsonArray](x, a => a))})
    this
  }

  /**
    * Executes the given SQL statement which may be an <code>INSERT</code>, <code>UPDATE</code>, or <code>DELETE</code>
    * statement.
    * @param sql the SQL to execute. For example <code>INSERT INTO table ...</code>
    * @param resultHandler the handler which is called once the operation completes.
    */
  def update(sql: String, resultHandler: Handler[AsyncResult[UpdateResult]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].update(sql.asInstanceOf[java.lang.String], {x: AsyncResult[JUpdateResult] => resultHandler.handle(AsyncResultWrapper[JUpdateResult, UpdateResult](x, a => UpdateResult(a)))})
    this
  }

  /**
    * Executes the given prepared statement which may be an <code>INSERT</code>, <code>UPDATE</code>, or <code>DELETE</code>
    * statement with the given parameters
    * @param sql the SQL to execute. For example <code>INSERT INTO table ...</code>
    * @param params these are the parameters to fill the statement.
    * @param resultHandler the handler which is called once the operation completes.
    */
  def updateWithParams(sql: String, params: io.vertx.core.json.JsonArray, resultHandler: Handler[AsyncResult[UpdateResult]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].updateWithParams(sql.asInstanceOf[java.lang.String], params, {x: AsyncResult[JUpdateResult] => resultHandler.handle(AsyncResultWrapper[JUpdateResult, UpdateResult](x, a => UpdateResult(a)))})
    this
  }

  /**
    * Calls the given SQL <code>PROCEDURE</code> which returns the result from the procedure.
    * @param sql the SQL to execute. For example <code>{call getEmpName`</code>.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def call(sql: String, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].call(sql.asInstanceOf[java.lang.String], {x: AsyncResult[JResultSet] => resultHandler.handle(AsyncResultWrapper[JResultSet, ResultSet](x, a => ResultSet(a)))})
    this
  }

  /**
    * Calls the given SQL <code>PROCEDURE</code> which returns the result from the procedure.
    *
    * The index of params and outputs are important for both arrays, for example when dealing with a prodecure that
    * takes the first 2 arguments as input values and the 3 arg as an output then the arrays should be like:
    *
    * <pre>
    *   params = [VALUE1, VALUE2, null]
    *   outputs = [null, null, "VARCHAR"]
    * </pre>
    * @param sql the SQL to execute. For example <code>{call getEmpName (?, ?)`</code>.
    * @param params these are the parameters to fill the statement.
    * @param outputs these are the outputs to fill the statement.
    * @param resultHandler the handler which is called once the operation completes. It will return a `ResultSet`.
    */
  def callWithParams(sql: String, params: io.vertx.core.json.JsonArray, outputs: io.vertx.core.json.JsonArray, resultHandler: Handler[AsyncResult[ResultSet]]): SQLOperations = {
    asJava.asInstanceOf[JSQLOperations].callWithParams(sql.asInstanceOf[java.lang.String], params, outputs, {x: AsyncResult[JResultSet] => resultHandler.handle(AsyncResultWrapper[JResultSet, ResultSet](x, a => ResultSet(a)))})
    this
  }

 /**
   * Like [[query]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryFuture(sql: String): scala.concurrent.Future[ResultSet] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JResultSet, ResultSet](x => ResultSet(x))
    asJava.asInstanceOf[JSQLOperations].query(sql.asInstanceOf[java.lang.String], promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[queryWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray): scala.concurrent.Future[ResultSet] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JResultSet, ResultSet](x => ResultSet(x))
    asJava.asInstanceOf[JSQLOperations].queryWithParams(sql.asInstanceOf[java.lang.String], params, promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[queryStream]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryStreamFuture(sql: String): scala.concurrent.Future[SQLRowStream] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JSQLRowStream, SQLRowStream](x => SQLRowStream(x))
    asJava.asInstanceOf[JSQLOperations].queryStream(sql.asInstanceOf[java.lang.String], promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[queryStreamWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def queryStreamWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray): scala.concurrent.Future[SQLRowStream] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JSQLRowStream, SQLRowStream](x => SQLRowStream(x))
    asJava.asInstanceOf[JSQLOperations].queryStreamWithParams(sql.asInstanceOf[java.lang.String], params, promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[querySingle]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def querySingleFuture(sql: String): scala.concurrent.Future[io.vertx.core.json.JsonArray] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JsonArray, io.vertx.core.json.JsonArray](x => x)
    asJava.asInstanceOf[JSQLOperations].querySingle(sql.asInstanceOf[java.lang.String], promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[querySingleWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def querySingleWithParamsFuture(sql: String, arguments: io.vertx.core.json.JsonArray): scala.concurrent.Future[io.vertx.core.json.JsonArray] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JsonArray, io.vertx.core.json.JsonArray](x => x)
    asJava.asInstanceOf[JSQLOperations].querySingleWithParams(sql.asInstanceOf[java.lang.String], arguments, promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[update]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def updateFuture(sql: String): scala.concurrent.Future[UpdateResult] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JUpdateResult, UpdateResult](x => UpdateResult(x))
    asJava.asInstanceOf[JSQLOperations].update(sql.asInstanceOf[java.lang.String], promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[updateWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def updateWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray): scala.concurrent.Future[UpdateResult] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JUpdateResult, UpdateResult](x => UpdateResult(x))
    asJava.asInstanceOf[JSQLOperations].updateWithParams(sql.asInstanceOf[java.lang.String], params, promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[call]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def callFuture(sql: String): scala.concurrent.Future[ResultSet] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JResultSet, ResultSet](x => ResultSet(x))
    asJava.asInstanceOf[JSQLOperations].call(sql.asInstanceOf[java.lang.String], promiseAndHandler._1)
    promiseAndHandler._2.future
  }

 /**
   * Like [[callWithParams]] but returns a [[scala.concurrent.Future]] instead of taking an AsyncResultHandler.
   */
  def callWithParamsFuture(sql: String, params: io.vertx.core.json.JsonArray, outputs: io.vertx.core.json.JsonArray): scala.concurrent.Future[ResultSet] = {
    val promiseAndHandler = handlerForAsyncResultWithConversion[JResultSet, ResultSet](x => ResultSet(x))
    asJava.asInstanceOf[JSQLOperations].callWithParams(sql.asInstanceOf[java.lang.String], params, outputs, promiseAndHandler._1)
    promiseAndHandler._2.future
  }

}
}
