package server;

class Message {
  int code;
  String content;

  protected Message(String input) {
    String[] getCode = input.split(" ", 2);
    this.code = Integer.parseInt(getCode[0]);
    this.content = getCode[1];
  }
}