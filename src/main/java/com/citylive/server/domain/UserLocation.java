package com.citylive.server.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User_Location")
public class UserLocation {
    @Id
    @NotNull
    private String userName;
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    private String locationType = "CURRENT"; //TODO make this as enum of address type.. Home or Remote
}
