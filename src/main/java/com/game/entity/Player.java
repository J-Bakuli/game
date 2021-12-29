package com.game.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "title")
    private String title;
    @Column(name = "race")
    @Enumerated(EnumType.STRING)
    private Race race;
    @Column(name = "profession")
    @Enumerated(EnumType.STRING)
    private Profession profession;
    @Column(name = "experience")
    private Integer experience;
    @Column(name = "level")
    private Integer level;
    @Column(name = "untilNextLevel")
    private Integer untilNextLevel;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "banned")
    private Boolean banned;

    public Player() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {return race;}

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {return profession;}

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {return experience;}

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {return birthday;}

    public void setBirthday(Date birthday) {this.birthday = birthday;}

    public Boolean getBanned() {return banned;}

    public void setBanned(Boolean banned) {this.banned = banned;}

    public int currentLevel() {return (int) (Math.sqrt(2500 + 200 * experience) - 50)/100;}
    public int nextLevel() {return (int) (50 * (currentLevel() + 1) * (currentLevel() + 2) - experience);}

    @Override
    public String toString() {
        return "Player{" +
                "id=" + getId() +
                ", name='" + name +
                ", title='" + title +
                ", race=" + race +
                ", profession=" + profession +
                ", experience=" + experience +
                ", level=" + level +
                ", untilNextLevel=" + untilNextLevel +
                ", birthday=" + birthday +
                ", banned=" + banned +
                '}';
    }
}
