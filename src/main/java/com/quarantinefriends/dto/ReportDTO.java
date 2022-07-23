package com.quarantinefriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReportDTO {

    private Long id;
    private UserDTO fromUser;
    private UserDTO toUser;
    private Date date;
}
