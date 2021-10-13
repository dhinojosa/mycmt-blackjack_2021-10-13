package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.Wallet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class WalletBettingTest {

    @Test
    public void walletWith12Bet8BalanceIs4() {
        // GIVEN (ARRANGE/SETUP)
        Wallet wallet = new Wallet();
        wallet.addMoney(12);

        // WHEN (ACT/PERFORM)
        wallet.bet(8);

        // THEN (ASSERT/VERIFY)
        assertThat(wallet.balance())
                .isEqualTo(12 - 8);
    }

    @Test
    public void walletWith27Bet7AndBet9BalanceIs11() {
        Wallet wallet = new Wallet();
        wallet.addMoney(27);

        wallet.bet(7);
        wallet.bet(9);

        assertThat(wallet.balance())
                .isEqualTo(27 - 7 - 9);
    }

    @Test
    public void walletWithMoneyWhenBetFullBalanceIsEmpty() {
        Wallet wallet = new Wallet();
        wallet.addMoney(33);

        wallet.bet(33);

        assertThat(wallet.isEmpty())
                .isTrue();
    }

    @Test
    public void betMoreThanBalanceThrowsException() {
        Wallet wallet = new Wallet();
        wallet.addMoney(73);

        assertThatThrownBy(() -> wallet.bet(74)).isInstanceOf(IllegalArgumentException.class);
    }

}