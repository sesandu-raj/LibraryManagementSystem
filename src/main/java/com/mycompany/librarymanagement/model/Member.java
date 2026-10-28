/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagement.model;

/**
 *
 * @author sesanduramath
 */
public class Member extends Person {
    
    public Member(String id, String name, String contactNumber){
        super(id,name,contactNumber);
        
    }
    
    
    @Override
    public String getRole() {
        return "Member";
    }
}
