package server;

public class Item {
    public String name;
    public double price;
    public double buyNow;
    public int time;

    public Item(String name, double price, double buyNow, int time){
        this.name = name;
        this.price = price;
        this.buyNow = buyNow;
        this.time = time;
    }

    public boolean bid(int bid){
        if(bid > price){
            price = bid;
            return true;
        } else {
            return false;
        }
    }
}
