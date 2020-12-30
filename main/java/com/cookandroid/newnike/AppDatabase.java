package com.cookandroid.newnike;

import java.util.ArrayList;
import java.util.List;

public class AppDatabase {
    private static List<Member> memberList;

    static {
        memberList = new ArrayList<>();

        Member member = null;

        member = new Member(1, "user1", "1111", "김영준");
        memberList.add(member);
        member = new Member(2, "user2", "2222", "김민병");
        memberList.add(member);
        member = new Member(3, "user3", "3333", "고우림");
        memberList.add(member);

        memberList.add(member);
    }

    public static Member findMember(String loginId, String loginPw) {
        for (Member member : memberList){
            if(member.getLoginId().equals(loginId)){
                return member;
            }
        }

        return null;
    }
    public static Member findMember(int id) {
        for (Member member : memberList){
            if(member.getId() == id){
                return member;
            }
        }

        return null;
    }
}
