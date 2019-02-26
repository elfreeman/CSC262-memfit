package edu.smith.cs.csc262.memfit;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Block {
    private String name;
    private int offset;
    private int size;

    /**
     * constructor for block
     */
    public Block(String name, int offset, int size){
        this.name = name;
        this.offset = offset;
        this.size = size;
    }

    public String toString(){
        String block ="Block \n\tName: "+name;
        block = block.concat("\n\tOffset: "+String.valueOf(offset));
        block = block.concat("\n\tSize: "+String.valueOf(size));
        return block;
    }

    public boolean is_adjacent(edu.smith.cs.csc262.memfit.Block other) { /* #2 on worksheet*/
        boolean adj = false;
        if(this.offset+this.size == other.getOffset()){
            adj = true;
        }
        return adj;
    }

    //getters and setters
    public void setName(String name) {
        this.name = name;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }
    //end of getters and setters section
}
