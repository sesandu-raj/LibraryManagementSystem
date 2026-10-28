/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagement.model;

/**
 *
 * @author sesanduramath
 */
public abstract class LibraryItem {
    
    private String itemId;
    private String title;
    private boolean isAvailable;
    
    public LibraryItem(String itemId, String title,boolean isAvailable){
        this.itemId = itemId;
        this.title = title;
        this.isAvailable = isAvailable;
        
        }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public abstract String getItemType();    
    
}
