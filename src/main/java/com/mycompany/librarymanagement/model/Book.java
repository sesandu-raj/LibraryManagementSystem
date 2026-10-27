/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagement.model;

/**
 *
 * @author sesanduramath
 */
public class Book extends LibraryItem{
    
    private String author;
    private String category;
    
    public Book(String itemId, String title, boolean isAvailable, String author, String category){
        super(itemId, title, isAvailable);
        this.author = author;
        this.category = category;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setauthor (String author){
        this.author = author;
    }
    
    public String getCategory(){
        return category;
    }
    
    public void setCategory (String category){
        this.category = category;
    }
   
  @override  
  public String getItemType(){
      return "Book";
  }
}
