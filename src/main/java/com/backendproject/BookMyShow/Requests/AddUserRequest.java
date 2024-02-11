package com.backendproject.BookMyShow.Requests;

import lombok.Data;

@Data
public class AddUserRequest {

    private String UserName;
    private String emailId;
}
