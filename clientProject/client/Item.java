// Copy-paste this file at the top of every file you turn in.
/*
 * EE422C Final Project submission by
 * Replace <...> with your actual data.
 * Tatsushi Matsumoto
 * trm2796
 * 16295
 * Spring 2020
 */

package client;

public class Item {
    int id;
    public String name;
    public double price;
    public double buyNow;
    public int time;
    public String customer;

    public Item(String name, double price, double buyNow, int time, int id, String customer){
        this.name = name;
        this.price = price;
        this.buyNow = buyNow;
        this.time = time;
        this.id = id;
        this.customer = customer;
    }

    public boolean bid(double bid){
        if(bid > price && bid < buyNow){
            price = bid;
            if(bid * 2 > buyNow){
                buyNow = bid*2;
            }
            return true;
        } else {
            return false;
        }
    }
}
