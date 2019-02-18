package edu.northeastern.ccs.im;


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
   Message bcst1 = Message.makeBroadcastMessage(null,"update: it is cold");
   assert(bcst1.toString().equals("BCT 2 -- 18 update: it is cold"));
   Message bcst2 = Message.makeBroadcastMessage(user,null);
   assert(bcst2.toString().equals("BCT 9 Aristotle 2 --"));
  }

  /**
   * Test NetworkConnection
   */
  @org.junit.jupiter.api.Test
  void testNetworkConnection1() {
  }


}
