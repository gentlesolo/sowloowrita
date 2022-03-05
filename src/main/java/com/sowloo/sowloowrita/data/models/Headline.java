package com.sowloo.sowloowrita.data.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
//@Table(name = "product")
@Entity
public class Headline {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private String industry;

    private double price;
//    private int quantity;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-ss HH-mm-ss")
    private LocalDateTime dateCreated;
//    private String imageUrl;
}
