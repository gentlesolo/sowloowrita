package com.sowloo.sowloowrita.data.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name")

    private String lastName;

    @Column(name = "email")

    private String emailId;

    @Column(name = "user_name")
    private String userName;

    private String imageUrl;


    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;

    @Transient
    private String generateEmployeeId;
}
