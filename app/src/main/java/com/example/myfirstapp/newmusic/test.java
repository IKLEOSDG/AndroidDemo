package com.example.myfirstapp.newmusic;

import java.lang.reflect.Array;

public class test {
  public static void main(String[] args){
    String a = "12345678";
    String b = "hello";
    String c = "xxx";
    String d = "23easd";
    String[][] abcd=new String[10][10];
    abcd[0][2]=a;
    abcd[9][9]=b;


  }
  public static void max(
      int value1,int value2,int max
  ){
    if(value1>value2)
      max = value1;
    else
      max = value2;
  }
}
