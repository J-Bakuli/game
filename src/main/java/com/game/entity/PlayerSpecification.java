package com.game.entity;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;

public class PlayerSpecification implements Specification<Player> {
    private String name;
    private String title;
    private Race race;
    private Profession profession;
    private Long after;
    private Long before;
    private Boolean banned;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minLevel;
    private Integer maxLevel;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Long getAfter() {return after;}

    public void setAfter(Long after) {this.after = after;}

    public Long getBefore() {return before;}

    public void setBefore(Long before) {this.before = before;}

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }

    public Integer getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(Integer maxExperience) {
        this.maxExperience = maxExperience;
    }

    public Integer getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public Specification<Player> and(Specification<Player> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Player> or(Specification<Player> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        ArrayList<Predicate> predicates = new ArrayList<>();

        if(name != null) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + name + "%"));
        }
        if(title != null) {
            predicates.add(cb.like(cb.lower(root.get("title")), "%" + title + "%"));
        }
        if(race != null) {
            predicates.add(cb.equal(root.<Race>get(("race")), race));
        }
        if(profession != null) {
            predicates.add(cb.equal(root.<Profession>get(("profession")), profession));
        }
        if(before != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("birthday"), new Date(before)));
        }
        if(after != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("birthday"), new Date(after)));
        }
        if(banned != null) {
            predicates.add(cb.equal(root.get("banned"), banned));
        }
        if(minExperience != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("experience"), minExperience));
        }
        if(maxExperience != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("experience"), maxExperience));
        }
        if(minLevel != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("level"), minLevel));
        }
        if(maxLevel != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("level"), maxLevel));
        }

        return predicates.size() <= 0 ? null : cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}

