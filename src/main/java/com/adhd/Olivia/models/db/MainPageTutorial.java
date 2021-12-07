package com.adhd.Olivia.models.db;


import javax.persistence.*;

@Entity
public class MainPageTutorial {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "tutorial_done")
    private boolean TutorialDone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
