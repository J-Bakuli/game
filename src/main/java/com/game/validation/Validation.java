package com.game.validation;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.GregorianCalendar;

public class Validation {

    public static void canBeCreated(Player player) {
        Validation.nullDataInserted(player);
        Validation.checkBirthday(player.getBirthday());
        Validation.checkExperience(player.getExperience());
        Validation.checkName(player.getName());
        Validation.checkTitle(player.getTitle());
    }

    public static void nullDataInserted(Player player) {
        if (player.getName() == null || player.getTitle() == null || player.getRace() == null ||
            player.getProfession() == null || player.getExperience() == null || player.getBirthday() == null ||
            player.getBanned() == null)
            throw new ValidationException();
    }

    public static void checkBirthday(Date birthday) {
        long birth = birthday.getTime();
        long begin = new GregorianCalendar(2000, 0, 0).getTimeInMillis();
        long end = new GregorianCalendar(3000, 0, 0).getTimeInMillis();
        if ((birth < begin) || (birth > end))
            throw new ValidationException();
    }

    public static void checkExperience(Integer experience) {
        if (experience > 10000000 || experience < 0)
            throw new ValidationException();
    }

    public static void checkId(String stringId) {
        long id;
        try {
            id = Long.parseLong(stringId);
        } catch (NumberFormatException e) {
            throw new ValidationException();
        }
        if (Long.parseLong(stringId) <= 0)
            throw new ValidationException();
    }

    public static void checkName(String name) {
        if (name.length() > 12)
            throw new ValidationException();
    }

    public static void checkTitle(String title) {
        if (title.length() > 30)
            throw new ValidationException();
    }

    public static void checkStatus(String status) {
        if (!status.equalsIgnoreCase("banned") || !status.equalsIgnoreCase("active"))
            throw new ValidationException();
    }

    public static void checkRace(String s) {
        try {
            Race.valueOf(s);
        } catch (Exception e) {
            throw new ValidationException();
        }
     }

    public static void checkProfession(String s) {
        try {
            Profession.valueOf(s);
        } catch (Exception e) {
            throw new ValidationException();
        }
    }

    public static void checkBanned(String banned) {
        Boolean booleanBanned;
        try {
            booleanBanned = Boolean.parseBoolean(banned);
        } catch (NumberFormatException e) {
            throw new ValidationException();
        }
    }
}

