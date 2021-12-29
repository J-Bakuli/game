package com.game.service;

import com.game.entity.Player;
import com.game.repository.PlayerRepository;
import com.game.validation.Validation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{
    private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void add(Player player) {
        player.setLevel(player.currentLevel());
        player.setUntilNextLevel(player.nextLevel());
        playerRepository.save(player);
    }

    @Override
    public Player getById(String id) {
        return playerRepository.findById(id).get();
    }

    @Override
    public void remove(String id) {
        playerRepository.deleteById(id);
    }

    @Override
    public void update(String id, Player player, Player updatedPlayer) {

        if (updatedPlayer.getName() != null) {
            Validation.checkName(updatedPlayer.getName());
            player.setName(updatedPlayer.getName());
        }

        if (updatedPlayer.getTitle() != null) {
            Validation.checkTitle(updatedPlayer.getTitle());
            player.setTitle(updatedPlayer.getTitle());
        }

        if (updatedPlayer.getRace() != null) {
            Validation.checkRace(String.valueOf(updatedPlayer.getRace()));
            player.setRace(updatedPlayer.getRace());
        }

        if (updatedPlayer.getProfession() != null) {
            Validation.checkProfession(String.valueOf(updatedPlayer.getProfession()));
            player.setProfession(updatedPlayer.getProfession());
        }

        if (updatedPlayer.getBirthday() != null) {
            Validation.checkBirthday(updatedPlayer.getBirthday());
            player.setBirthday(updatedPlayer.getBirthday());
        }

        if (updatedPlayer.getBanned() != null) {
            Validation.checkBanned(String.valueOf(updatedPlayer.getBanned()));
            player.setBanned(updatedPlayer.getBanned());
        }

        if (updatedPlayer.getExperience() != null) {
            Validation.checkExperience(updatedPlayer.getExperience());
            player.setExperience(updatedPlayer.getExperience());
        }

        add(player);
    }

    @Override
    public Page<Player> listPlayers(Specification<Player> spec, Pageable page) {
        return playerRepository.findAll(spec, page);
    }
}


