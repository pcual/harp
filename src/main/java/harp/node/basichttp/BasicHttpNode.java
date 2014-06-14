/*
 * Copyright 2014 Yash Parghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package harp.node.basichttp;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import harp.node.Node;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 * A node that is managed by HTTP requests.
 */
class BasicHttpNode implements Node {

  // TODO port configuration
  private static final int PORT = 9321;

  @Override
  public void start() {
    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
      server.createContext("/", new HelloHandler());
      System.out.println("Running BasicHttpNode...");
      server.start();
    } catch (IOException ex) {
      // TODO better exception handling
      throw new RuntimeException(ex);
    }
  }

  @Override
  public void stop() {
    throw new UnsupportedOperationException("TODO");
  }

  private static class HelloHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
      // Thanks to: http://stackoverflow.com/a/3732328
      String response = "Hello!";
      exchange.sendResponseHeaders(200, response.length());
      OutputStream os = exchange.getResponseBody();
      os.write(response.getBytes());
      os.close();
    }
  }
}
