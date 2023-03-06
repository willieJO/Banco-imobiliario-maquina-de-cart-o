package br.edu.ifsp.dmos5.Model;

import java.util.ArrayList;

public class StarBank {
    private static StarBank instance;
    private ArrayList<CreditCard> cards;
    public StarBank() {}

    public static StarBank getInstance() {
        if (instance == null) {
            instance = new StarBank();
        }
        return instance;
    }
    public void startCreditCards(int quantidadeJogadores) {
        for (int i = 0; i < quantidadeJogadores; i++) {
            cards.add(new CreditCard());
        }
    }

}
