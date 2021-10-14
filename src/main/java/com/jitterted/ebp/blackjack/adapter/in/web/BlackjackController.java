package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.Supplier;

@Controller
public class BlackjackController {

    private Supplier<Game> gameFactory;
    private Game currentGame;

    public BlackjackController(Supplier<Game> gameFactory) {
        this.gameFactory = gameFactory;
    }

    @PostMapping("/start-game")
    public String startGame() {
        currentGame = gameFactory.get();
        currentGame.initialDeal();
        return "redirect:/game";
    }

    @GetMapping("/game")
    public String viewGame(Model model) {
        model.addAttribute("gameView", GameView.of(currentGame));
        return "blackjack";
    }

}