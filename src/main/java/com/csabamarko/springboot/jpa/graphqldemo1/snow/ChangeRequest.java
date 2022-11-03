package com.csabamarko.springboot.jpa.graphqldemo1.snow;

import com.csabamarko.springboot.jpa.graphqldemo1.RootEntity;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CHANGE_REQUEST", schema = "SNOW")
public class ChangeRequest extends RootEntity<String> {

    @Id
    @Column(name = "ID")
    private String id;

    @Column(name = "CHANGE_NUMBER")
    private String changeNumber;

    @Column(name = "SHORT_DESCRIPTION")
    private String shortDescription;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE_INDICATOR")
    private Boolean active;

    @Column(name = "CHANGE_REQUEST_TYPE")
    private String type;

    @Column(name = "WORK_START_DATE")
    private OffsetDateTime workStart;

    @Column(name = "WORK_END_DATE")
    private OffsetDateTime workEnd;

    @Column(name = "TIME_WORKED_HOURS")
    private Integer timeWorkedHours;

    @Column(name = "CREATED_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "UPDATED_TIMESTAMP")
    private OffsetDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ASSIGNED_TO_USER_ID")
    private User assignedTo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY_USER_ID")
    private User createdBy;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REQUESTED_BY_USER_ID")
    private User requestedBy;

    @ManyToMany()
    @JoinTable(name = "CHANGE_REQUEST_X_AFFECTED_USERS",
        joinColumns = @JoinColumn(name = "CHANGE_REQUEST_ID"), inverseJoinColumns = @JoinColumn(name = "USERS_ID")
    )
    private List<User> affectedUsers;

}
