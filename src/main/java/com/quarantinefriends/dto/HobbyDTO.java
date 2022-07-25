package com.quarantinefriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class HobbyDTO {
    private Long id;
    private String name;

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }

        final HobbyDTO other = (HobbyDTO) object;
        if (this.id == other.getId()) {
            return true;
        }
        return false;
    }
}
