package com.citylive.server.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_Role")
public class UserRole {
    @Id
    private String userName;
    private String role = "ROLE_USER";
}
