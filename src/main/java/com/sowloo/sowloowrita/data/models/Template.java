package com.sowloo.sowloowrita.data.models;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;

    @OneToOne(cascade = CascadeType.PERSIST)
    private AppUser appuser;

    @OneToOne(cascade = CascadeType.PERSIST)
    private TemplateCat category;
    @CreationTimestamp
    private LocalDateTime dateCreated;

}
