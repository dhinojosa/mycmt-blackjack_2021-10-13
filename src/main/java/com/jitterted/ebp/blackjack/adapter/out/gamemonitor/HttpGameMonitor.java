package com.jitterted.ebp.blackjack.adapter.out.gamemonitor;

import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.port.GameMonitor;
import org.springframework.web.client.RestTemplate;

public class HttpGameMonitor implements GameMonitor {

    @Override
    public void roundCompleted(Game game) {
        post("https://blackjack-game-monitor.herokuapp.com/api/gameresults", GameResultDto.of(game));
    }

    public void post(String uri, GameResultDto gameResultDto){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(uri, gameResultDto, GameResultDto.class);
    }
}
