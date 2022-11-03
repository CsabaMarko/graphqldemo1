package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS", schema = "SNOW") // H2 doesn't allow "USER"; probably reserved word?
public class User extends RootEntity<String> {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "SN_ID")
    private String snId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "PHONE_NUMBER")
    private String phone;

    @Column(name = "EMAIL_ADDRESS")
    private String email;

    // This the "inverse" mapping. The "forward" is defined on ChangeRequest.affectedUsers:
    @ManyToMany(mappedBy = "affectedUsers")
    private List<ChangeRequest> affectedByChangeRequests;

}
