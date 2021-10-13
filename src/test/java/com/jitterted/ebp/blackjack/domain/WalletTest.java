package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.Wallet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class WalletTest {

    @Test
    public void newWalletIsEmpty() {
        Wallet wallet = new Wallet();

        assertThat(wallet.isEmpty())
                .isTrue();
    }

    @Test
    public void newWalletAddMoneyIsNotEmpty() {
        Wallet wallet = new Wallet();

        wallet.addMoney(10);

        assertThat(wallet.isEmpty())
                .isFalse();
    }

    @Test
    public void newWalletHasZeroBalance() {
        Wallet wallet = new Wallet();

        assertThat(wallet.balance())
                .isZero();
    }

    @Test
    public void newWalletAdd15HasBalanceOf15() {
        Wallet wallet = new Wallet();

        wallet.addMoney(15);

        assertThat(wallet.balance())
                .isEqualTo(15);
    }

    @Test
    public void newWalletAdd17And18HasBalanceOf35() {
        Wallet wallet = new Wallet();

        wallet.addMoney(17);
        wallet.addMoney(18);

        assertThat(wallet.balance())
                .isEqualTo(17 + 18);
    }

    @Test
    public void addMoneyOfLessThanZeroThrowsException() {
        Wallet wallet = new Wallet();

        assertThatThrownBy(() -> wallet.addMoney(-1)).isInstanceOf(IllegalArgumentException.class);
    }

}
