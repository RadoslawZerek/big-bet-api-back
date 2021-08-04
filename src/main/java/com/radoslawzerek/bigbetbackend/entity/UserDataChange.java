package com.radoslawzerek.bigbetbackend.entity;


import com.radoslawzerek.bigbetbackend.enums.ChangedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import java.time.LocalDateTime;

@NamedNativeQuery(
        name = "UserDataChange.getDataChangesOfUser",
        query = "SELECT * FROM USER_DATA_CHANGES " +
                "WHERE USER_ID = :USER_ID " +
                "ORDER BY CHANGE_TIME DESC",
        resultClass = UserDataChange.class
)

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity(name = "USER_DATA_CHANGES")
public class UserDataChange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "CHANGED_VALUE")
    private ChangedValue changedValue;

    @Column(name = "OLD_VALUE")
    private String oldValue;

    @Column(name = "NEW_VALUE")
    private String newValue;

    @Column(name = "CHANGE_TIME")
    private LocalDateTime changeTime;

    public UserDataChange(User user, ChangedValue changedValue, String oldValue, String newValue) {
        this.user = user;
        this.changedValue = changedValue;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.changeTime = LocalDateTime.now();
    }
}
