package edu.smith.cs.csc262.memfit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Simulation {
  public static char nextBlockName = 'A';
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

  private static void blockSplit(Block freeBlock, int req_size){
    if(freeBlock.getSize() > req_size){
      Block newBlock = new Block(String.valueOf(nextBlockName++), freeBlock.getOffset(), req_size);
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

    Block myB = new Block("hello", 0, 1);
    Block myB1 = new Block("hello again", 1, 4);
    Block myB2 = new Block("this is another block", 6, 1);
    Block myB3 = new Block("this is another block", 7, 1);
    Block myB4 = new Block("this is another block", 8, 1);

    free_list.add(myB);
    free_list.add(myB1);
    free_list.add(myB2);
    free_list.add(myB3);
    free_list.add(myB4);
    print();

    compactFreeList();
    print();

  /* #9 on worksheet (modified) */ }
}
