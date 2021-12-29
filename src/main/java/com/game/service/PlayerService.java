package com.game.service;

import com.game.entity.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface PlayerService {
    void add(Player player);

    Player getById(String id);

    void remove(String id);

    void update(String id, Player player, Player updatedPlayer);

    Page<Player> listPlayers(Specification<Player> specification, Pageable page);
}