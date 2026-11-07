/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagement.db;

import com.mycompany.librarymanagement.model.Member;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    // Using an ArrayList to store system data as requested[cite: 2]
    private List<Member> members;

    public MemberDAO() {
        this.members = new ArrayList<>();
        // Pre-load sample data
        members.add(new Member("M001", "Nimal Perera", "0771234567"));
        members.add(new Member("M002", "Kasun Silva", "0719876543"));
    }

    public boolean addMember(Member member) {
        return members.add(member);
    }

    public List<Member> getAllMembers() {
        return members;
    }

    public Member getMemberById(String memberId) {
        for (Member member : members) {
            if (member.getId().equals(memberId)) {
                return member;
            }
        }
        return null;
    }

    public boolean deleteMember(String memberId) {
        return members.removeIf(member -> member.getId().equals(memberId));
    }
}
