package com.lakshman.todo.user;

import org.springframework.stereotype.Component;

@Component
public class UserHelper {

    public String getFirstName(String name) {
        if (name == null)
            return "";
        String firstName = name.split(" ")[0];
        return firstName;
    }

    public String getLasttName(String name) {
        if (name == null)
            return "";
        String nameArray[] = name.split(" ");
        StringBuilder lastName = new StringBuilder();
        for (int i = 1; i < nameArray.length; i++) {
            lastName.append(nameArray[i] + " ");
        }
        return lastName.toString();
    }

    public String getUserEmailFromSecurity() {
        return null;
    }
}