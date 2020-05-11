package client;

public class Item {
    int id;
    public String name;
    public double price;
    public double buyNow;
    public int time;

    public Item(String name, double price, double buyNow, int time, int id){
        this.name = name;
        this.price = price;
        this.buyNow = buyNow;
        this.time = time;
        this.id = id;
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
