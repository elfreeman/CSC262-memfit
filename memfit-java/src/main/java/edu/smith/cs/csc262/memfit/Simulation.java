package edu.smith.cs.csc262.memfit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Simulation {
  public static List<Block> free_list;
  public static List<Block> used_list;

  /**
   * prints out all elements in the free and used lists
   */
  public static void print(){
    System.out.println("Free List: ");
    for(Block b : free_list){
      String block = b.toString();
      System.out.println(block);
    }
    System.out.println("Used List: ");
    for(Block b: used_list){
      String block = b.toString();
      System.out.println(block);
    }
  }

  private static void blockSplit(Block freeBlock, int req_size, String name){
    if(freeBlock.getSize() > req_size){
      Block newBlock = new Block(name, freeBlock.getOffset(), req_size);
      freeBlock.setOffset(freeBlock.getOffset()+req_size);
      freeBlock.setSize(freeBlock.getSize()-req_size);
      used_list.add(newBlock);
      return;
    }else{
      //There was an error if we got passed a block that can't fit the request,
      System.err.println("There wasn't enough memory to allocate the new block");
    }
  }

  private static void compactFreeList(){
    ByOffset comparator = new ByOffset();
    Collections.sort(free_list, comparator);
    for(int i=0; i<free_list.size()-1; i++){
      if(free_list.get(i).is_adjacent(free_list.get(i+1))){
        free_list.get(i).setSize(free_list.get(i).getSize()+free_list.get(i+1).getSize());
        free_list.remove(free_list.get(i+1));
        i = i-1;
      }
    }
  }

  private static void alloc_first(String name, int size){
    for(int i=0; i<free_list.size(); i++){
      if(size <= free_list.get(i).getSize()){
        blockSplit(free_list.get(i), size, name);
        return;
      }
    }
    //If we reach here, none of the blocks are big enough to allocate memory of that size
    System.err.println("Allocation Error! Not enough space!");
    return;
  }

  /*Block alloc(String name, int size) { //#4,#5,#6 on worksheet  }
  private Block alloc_first(String name, int size);
  void free(String name);
  private void splitBlock(Block b, String newName, int size) { // #1 on worksheet  }
  private void compactFreeList() { // #3 on worksheet }
*/

  public static void main(String[] args) {
    //for now just some test statements
    used_list = new ArrayList<Block>();
    free_list = new ArrayList<Block>();

    Block pool = new Block("pool", 0, 1000);
    free_list.add(pool);

    /*Block myB = new Block("zero", 0, 1);
    Block myB1 = new Block("one", 1, 4);
    Block myB2 = new Block("two", 6, 1);
    Block myB3 = new Block("three", 7, 1);
    Block myB4 = new Block("four", 8, 1);

    free_list.add(myB);
    free_list.add(myB1);
    free_list.add(myB2);
    free_list.add(myB3);
    free_list.add(myB4);
    print();

    compactFreeList();
    */
    alloc_first("new block", 10);
    alloc_first("second new block", 25);
    alloc_first("let's break this", 1000);
    print();

  /* #9 on worksheet (modified) */ }
}
