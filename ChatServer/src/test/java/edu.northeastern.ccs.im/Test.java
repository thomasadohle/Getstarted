package edu.northeastern.ccs.im;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import edu.northeastern.ccs.im.server.ClientRunnable;
import edu.northeastern.ccs.im.server.ServerConstants;

class Test {

  /**
   * Test MessageType enum.
   */
  @org.junit.jupiter.api.Test
  void testMessageType() {
    MessageType hello = MessageType.HELLO;
    MessageType bye = MessageType.QUIT;
    MessageType bcst = MessageType.BROADCAST;
    assert ("HLO".equals(hello.toString()));
    assert ("BYE".equals(bye.toString()));
    assert ("BCT".equals(bcst.toString()));
  }

  /**
   * Test Message.java methods except makeMessage and toString
   */
  @org.junit.jupiter.api.Test
  void testMessage1() {
    String name = "Thomas";
    Message quit = Message.makeQuitMessage(name);
    assert (quit.getName().equals(name));
    Message bcst = Message.makeBroadcastMessage(name, "Nice weather");
    assert (bcst.getText().equals("Nice weather"));
    Message hello = Message.makeHelloMessage("Hi there!");
    assert (hello.getName() == null);
    Message simpleHello = Message.makeSimpleLoginMessage(name);
    assert (simpleHello.getText() == null);
    assert (quit.terminate());
    assert (hello.isInitialization());
    assert (bcst.isBroadcastMessage());
  }

  /**
   * Test Message.java method makeMessage
   */
  @org.junit.jupiter.api.Test
  void testMessage2() {
    String name = "Charles Darwin";
    Message quit = Message.makeMessage("BYE", name, null);
    assert (quit.terminate());
    Message hello = Message.makeMessage("HLO", name, null);
    assert (hello.isInitialization());
    Message broadcast = Message.makeMessage("BCT", name, "I have tried lately to read " +
            "Shakespeare, and found it so intolerably dull that it nauseated me.");
    assert (broadcast.isBroadcastMessage());
  }

  /**
   * Test Message.java method toString
   */
  @org.junit.jupiter.api.Test
  void testMessage3() {
    String user = "Aristotle";
    Message bcst1 = Message.makeBroadcastMessage(null, "update: it is cold");
    assert (bcst1.toString().equals("BCT 2 -- 18 update: it is cold"));
    Message bcst2 = Message.makeBroadcastMessage(user, null);
    assert (bcst2.toString().equals("BCT 9 Aristotle 2 --"));
  }


  /**
   * Test NetworkConnection
   */
  @org.junit.jupiter.api.Test
  void testNetworkConnection1() {
    SocketChannel sockChan = null;
    NetworkConnection netConn = null;
    try {
      sockChan = SocketChannel.open();
      sockChan.configureBlocking(false);
      sockChan.socket().bind((new InetSocketAddress(5252)));
      netConn = new NetworkConnection(sockChan);
      Message msg = Message.makeSimpleLoginMessage("Thomas");
      assert (netConn.sendMessage(msg));

      //netConn.iterator();
    } catch (IOException e) {
      System.out.println(e.toString());
    } catch (NotYetConnectedException n) {
      System.out.println("NotYetconnected from line 94 " + n.toString());
    }

    ClientRunnable cR = null;
    Message msg1 = Message.makeSimpleLoginMessage("Thomas");
    try {
      cR = new ClientRunnable(netConn);
      cR.enqueueMessage(msg1);
      cR.setName("Joe");
    } catch (NullPointerException n) {
      System.out.println("Null pointer exception from line 104" + n.toString());
    }
    assert (cR.getName().equals("Joe"));
    cR.run();
    assert (!cR.isInitialized());
  }


  @org.junit.jupiter.api.Test
  void testClientRunnable() throws Exception {
    Selector sel = Selector.open();
    ServerSocketChannel sc = ServerSocketChannel.open();

    InetSocketAddress host = new InetSocketAddress("localhost", 3654);
    sc.bind(host);
    sc.configureBlocking(false);
    int ops = sc.validOps();
    SelectionKey key = sc.register(sel, ops, null);






  }
}