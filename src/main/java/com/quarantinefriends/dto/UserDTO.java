package com.quarantinefriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private Integer age;
    private String email;
    private String password;
    private String photo;
    private RoleDTO role;
    private List<HobbyDTO> hobbies;
    private List<PreferenceDTO> preferences;
    private boolean accountTerminated;

    @Override
    public boolean equals(Object object) {
        if(object == null) {
            return false;
        }
        if(object.getClass() != this.getClass()) {
            return false;
        }

        final UserDTO other = (UserDTO) object;
        if(this.id == other.getId()) {
            return true;
        }
        return false;
    }
}
