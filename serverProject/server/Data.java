package server;


import java.io.*;
import java.util.ArrayList;

public class Data {

    private static ArrayList<Item> items;

    public Data(){
        items = new ArrayList<>();
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
                price = Integer.parseInt(br.readLine());
                buyNow = Integer.parseInt(br.readLine());
                time = Integer.parseInt(br.readLine());
                Item add = new Item(name, price, buyNow, time);
                items.add(add);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void list(Item add){
        items.add(add);
    }

    public ArrayList<Item> getItems(){
        return items;
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
        return result.toString();
    }

    public String getPriceUpdateMessage(Item updater){
        return "1 " + updater.name + " " + updater.price;
    }

    public void clear(){
        items = new ArrayList<>();
    }

}
