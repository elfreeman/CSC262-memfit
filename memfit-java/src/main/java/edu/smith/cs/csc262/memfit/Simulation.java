package edu.smith.cs.csc262.memfit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

  private static void free(String name){
    //loop through used_list to find the right block
    Block block;
    for(int i=0; i<used_list.size(); i++){
      block = used_list.get(i);
      if(block.getName().equals(name)){
        //we found our block, now remove it from used and add to free
        free_list.add(block);
        used_list.remove(block);
        compactFreeList();
        return;
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
    //initialize the lists
    used_list = new ArrayList<Block>();
    free_list = new ArrayList<Block>();

    //now read in memory allocation instructions from user;
    //assume correct format for input lines
    String fileName = "/Users/elizabethfreeman/IdeaProjects/CSC262-memfit/input.txt";
    List<String> lines;
    try {
      lines = Files.readAllLines(Paths.get(fileName));
    } catch (IOException e) {
      e.printStackTrace();
      lines = null;
    }
    int lineCounter = 1;
    for(String line: lines){
      String[] words = line.split(" ");
      if(words.length < 2){
        //all valid lines will have at least 2 words, ignore lines with fewer args
        continue;
      }
      String command = words[0];
      switch(command){
        case "pool":
          Block pool = new Block(words[1], 0, Integer.valueOf(words[2]));
          free_list.add(pool);
          break;
        case "alloc":
          alloc_first(words[1], Integer.valueOf(words[2]));
          break;
        case "free":
          free(words[1]);
          break;
        default:
          System.err.println("Invalid command in text file line "+String.valueOf(lineCounter)+"\n"+line);
          break;
      }
      lineCounter++;
    }

    print();


    /* #9 on worksheet (modified) */ }
}
