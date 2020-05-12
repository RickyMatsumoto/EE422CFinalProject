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

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;

public class Data {

    private static ArrayList<Item> items;
    private static ArrayList<Item> sold;

    public Data(){
        items = new ArrayList<>();
        sold = new ArrayList<>();
        parse("server/items.txt");
        for(Item item : items){
            System.out.println(item.name);
        }
    }

    private void parse(String inFile){
        try{
            File in = new File(inFile);
            BufferedReader br = new BufferedReader(new FileReader(in));
            String name = br.readLine();
            int numItems = Integer.parseInt(name);
            double price;
            double buyNow;
            int time;
            for(int i = 0; i < numItems; i++){
                name = br.readLine();
                price = Double.parseDouble(br.readLine());
                buyNow = Double.parseDouble(br.readLine());
                time = Integer.parseInt(br.readLine());
                Item add = new Item(name, price, buyNow, time, null);
                items.add(add);
            }
            name = br.readLine();
            numItems = Integer.parseInt(name);
            for(int i = 0; i < numItems; i++){
                name = br.readLine();
                price = Double.parseDouble(br.readLine());
                Item sold = new Item(name, price, 0, -1, br.readLine());
                sold(sold);
            }
            Timer timer = new Timer();
            timer.schedule(new countdownTimer(), 0, 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String list(Item add){
        items.add(add);
        return("4 " + add.name + " " + add.price + " " + add.buyNow + " " + add.time);
    }

    public void sold(Item add){
        sold.add(add);
        if(sold.size() > 7) {
            sold.remove(0);
        }
    }

    public static ArrayList<Item> getItems(){
        return items;
    }

    public static ArrayList<Item> getSold() {
        return sold;
    }

    public Item getItem(String name){
        for(Item item : items){
            if(item.name.equals(name)){
                return item;
            }
        }
        return null;
    }

    public String getInitMessage(){
        StringBuilder result = new StringBuilder();
        result.append("0 ").append(items.size());
        for(Item item : items){
            result.append(" ").append(item.name).append(" ").append(item.price).append(" ").append(item.buyNow).append(" ").append(item.time);
        }
        result.append(" ").append(sold.size());
        for(Item item : sold){
            result.append(" ").append(item.name).append(" ").append(item.price).append(" ").append(item.customer);
        }
        return result.toString();
    }

    public String getPriceUpdateMessage(Item updater){
        return "1 " + updater.name + " " + updater.price + " " + updater.buyNow + " " + updater.customer;
    }

    public void clear(){
        items = new ArrayList<>();
    }


    public String closeBidding(Item item) {
        sold(item);
        items.remove(item);
        return "3 " + item.name + " " + item.price + " " + item.customer;
    }
}
