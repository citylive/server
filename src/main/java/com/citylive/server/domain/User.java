package com.citylive.server.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User")
public class User {
    @NotNull
    @Id
    private String userName;
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private String password;
    private Boolean enabled = true;
    private String deviceId;
}
