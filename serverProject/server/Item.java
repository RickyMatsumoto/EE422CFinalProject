package server;

public class Item {
    public String name;
    public double price;
    public double buyNow;
    public int time;
    public String customer;

    public Item(String name, double price, double buyNow, int time, String customer){
        this.name = name;
        this.price = price;
        this.buyNow = buyNow;
        this.time = time;
        this.customer = customer;
    }

    public boolean bid(double bid, String customer){
        if(bid > price && bid < buyNow){
            price = bid;
            this.customer = customer;
            if(bid * 2 > buyNow){
                buyNow = bid*2;
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean decrementSecond(){
        time--;
        return time > 0;
    }
}
