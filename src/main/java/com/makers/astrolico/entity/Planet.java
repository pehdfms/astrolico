package com.makers.astrolico.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "planets")
@SQLDelete(sql = "UPDATE planets SET removed = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "removed IS null")
@NoArgsConstructor
@Getter @Setter
public class Planet extends AbstractEntity {
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "explanation", nullable = false)
    private String explanation;

    @URL
    @Column(name = "hdurl")
    private String hdurl;

    @URL()
    @Column(name = "url")
    private String url;
}
