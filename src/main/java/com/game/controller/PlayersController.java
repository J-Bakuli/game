package com.game.controller;

import com.game.entity.Player;
import com.game.entity.PlayerSpecification;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import com.game.validation.PlayerNotFoundbyIdException;
import com.game.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@RestController
@RequestMapping("/rest/players")
    public class PlayersController {

    private final PlayerService playerService;
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public PlayersController(PlayerService playerService) {
        this.playerService = playerService;
    }

    /** 1 Get all players */
    @GetMapping()
    public ResponseEntity<List<Player>> listPlayers(@RequestParam(value = "name", required = false) String name,
                                               @RequestParam(value = "title", required = false) String title,
                                               @RequestParam(value = "race", required = false) Race race,
                                               @RequestParam(value = "profession", required = false) Profession profession,
                                               @RequestParam(value = "after", required = false) Long after,
                                               @RequestParam(value = "before", required = false) Long before,
                                               @RequestParam(value = "banned", required = false) Boolean banned,
                                               @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                               @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                               @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                               @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                               @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                               @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                               PlayerSpecification playerSpecification) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        return new ResponseEntity<>(playerService.listPlayers(setSpec(name, title, race, profession, after, before, banned,
                                                                      minExperience, maxExperience, minLevel, maxLevel,
                                                                      playerSpecification), page).getContent(), HttpStatus.OK);
    }

    /** 2 Create player */
    @PostMapping()
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Validation.canBeCreated(player);
        playerService.add(player);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    /** 3 Delete player by id */
    @DeleteMapping("/{id}")
    public ResponseEntity<Player> deletePlayer(@PathVariable("id") String id) {
        Validation.checkId(id);
        try {
            Player player = playerService.getById(id);
            playerService.remove(id);
        } catch (Exception e) {
            throw new PlayerNotFoundbyIdException();
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /** 4 Get player by id */
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable("id") String id) {
        Validation.checkId(id);
        Player player;
        try {
            player = playerService.getById(id);
        } catch (Exception e) {
            throw new PlayerNotFoundbyIdException();
        }
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    /**5 Update player */
    @PostMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") String id,
                                               @RequestBody(required = false) Player updatedPlayer) {
        Player player;
        Validation.checkId(id);

        try {
            player = playerService.getById(id);
        } catch (Exception e) {
            throw new PlayerNotFoundbyIdException();
        }

        if (updatedPlayer.getName() == null && updatedPlayer.getTitle() == null && updatedPlayer.getRace() == null &&
                updatedPlayer.getProfession() == null && updatedPlayer.getBirthday() == null &&
                updatedPlayer.getBanned() == null && updatedPlayer.getExperience() == null) {
            updatedPlayer = null;
            return new ResponseEntity<>(player, HttpStatus.OK);
        } else {
            playerService.update(id, player, updatedPlayer);
            return new ResponseEntity<>(player, HttpStatus.OK);
        }
    }

    /** 6 Count players */
    @GetMapping("/count")
    public ResponseEntity<Integer> getPlayersCount(@RequestParam(value = "name", required = false) String name,
                                                   @RequestParam(value = "title", required = false) String title,
                                                   @RequestParam(value = "race", required = false) Race race,
                                                   @RequestParam(value = "profession", required = false) Profession profession,
                                                   @RequestParam(value = "after", required = false) Long after,
                                                   @RequestParam(value = "before", required = false) Long before,
                                                   @RequestParam(value = "banned", required = false) Boolean banned,
                                                   @RequestParam(value = "minExperience", required = false) Integer minExperience,
                                                   @RequestParam(value = "maxExperience", required = false) Integer maxExperience,
                                                   @RequestParam(value = "minLevel", required = false) Integer minLevel,
                                                   @RequestParam(value = "maxLevel", required = false) Integer maxLevel,
                                                   @RequestParam(value = "order", required = false, defaultValue = "ID") PlayerOrder order,
                                                   @RequestParam(value = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "3") Integer pageSize,
                                                   PlayerSpecification playerSpecification) {
        Pageable page = PageRequest.of(pageNumber, pageSize, Sort.by("id").ascending());
        return new ResponseEntity<>((int)playerService.listPlayers(setSpec(name, title, race, profession, after, before, banned,
                                                                      minExperience, maxExperience, minLevel, maxLevel,
                                                                      playerSpecification), page).getTotalElements(), HttpStatus.OK);
    }

    private PlayerSpecification setSpec(String name, String title, Race race, Profession profession, Long after,
                                        Long before, Boolean banned, Integer minExperience, Integer maxExperience,
                                        Integer minLevel, Integer maxLevel, PlayerSpecification playerSpecification) {
        if (name != null) playerSpecification.setName(name);
        if (title != null) playerSpecification.setTitle(title);
        if (race != null) playerSpecification.setRace(race);
        if (profession != null) playerSpecification.setProfession(profession);
        if (after != null) playerSpecification.setAfter(after);
        if (before != null) playerSpecification.setBefore(before);
        if (banned != null) playerSpecification.setBanned(banned);
        if (minLevel != null) playerSpecification.setMinLevel(minLevel);
        if (maxLevel != null) playerSpecification.setMaxLevel(maxLevel);
        if (minExperience != null) playerSpecification.setMinExperience(minExperience);
        if (maxExperience != null) playerSpecification.setMaxExperience(maxExperience);
        return playerSpecification;
    }
}

