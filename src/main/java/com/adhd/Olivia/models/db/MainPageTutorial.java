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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTutorialDone() {
        return TutorialDone;
    }

    public void setTutorialDone(boolean tutorialDone) {
        TutorialDone = tutorialDone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
