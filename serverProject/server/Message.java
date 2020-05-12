// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * Tatsushi Matsumoto
 * trm2796
 * 16295
 * Spring 2020
 */

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