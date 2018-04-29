package com.stevenp.hibernate.OneToManyUni.entity;

import javax.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    // define fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "comment")
    private String comment;


    // constructors

    public Review() {
    }

    public Review(String comment) {
        this.comment = comment;
    }


    // getters & setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    // toString

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                '}';
    }

}
