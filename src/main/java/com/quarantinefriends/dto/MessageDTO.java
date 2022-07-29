package com.quarantinefriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MessageDTO {

    private Long id;
    private String message;
    private UserDTO fromUser;
    private UserDTO toUser;
}
