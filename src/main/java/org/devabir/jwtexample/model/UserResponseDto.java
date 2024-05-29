package org.devabir.jwtexample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String email;
    private Date createdAt;
    private Date updatedAt;

}
