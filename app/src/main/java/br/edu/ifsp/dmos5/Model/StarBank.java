package br.edu.ifsp.dmos5.Model;

import java.util.ArrayList;

import br.edu.ifsp.dmos5.Utils.ExceptionUtils;

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

    public void startCreditCards() {
        for (int i = 0; i < 6; i++) {
            cards.add(new CreditCard());
        }
    }

    public void roundCompleted(CreditCard card, double valor) {
        card.creditValue(valor);
    }

    public boolean transfer(CreditCard card,CreditCard receptor, double valor) {
        try {
            card.debitValue(valor);
            return true;
        } catch (ExceptionUtils ex) {
            return false;
        }
    }

    public void recive(CreditCard card, double valor) {
        card.creditValue(valor);
    }
    public boolean play(CreditCard card,double valor) {
        return true; // ?????/
    }

}
