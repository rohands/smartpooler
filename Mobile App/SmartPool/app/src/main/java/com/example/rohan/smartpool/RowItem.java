package com.example.rohan.smartpool;

/**
 * Created by rohan on 21/4/16.
 */
public class RowItem {

    private String name,src,dest,dist;


    public RowItem(String src, String dest,String dist,String name) {
        this.name = name;
        this.src = src;
        this.dest = dest;
        this.dist = dist;
    }


    @Override
    public String toString() {
        return name + "\n" + dist;
    }

    public String getName() {
        return name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDist() {
        return dist;
    }

    public void setDist(String dist) {
        this.dist = dist;
    }
}
