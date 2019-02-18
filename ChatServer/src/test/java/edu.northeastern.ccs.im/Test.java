package edu.northeastern.ccs.im;

public class Test {

  @org.junit.jupiter.api.Test
  public void testMessage(){
    MessageType hello = MessageType.HELLO;
    MessageType bye = MessageType.QUIT;
    MessageType bcst = MessageType.BROADCAST;
    assert("HLO" == hello.toString());
    assert("BYE" == bye.toString());
    assert("BCT" == bcst.toString());
  }


}
