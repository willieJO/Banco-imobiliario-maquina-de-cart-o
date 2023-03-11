package br.edu.ifsp.dmos5.Model;

import android.widget.Button;

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

    public void startCreditCards(ArrayList<Button> botoes) {
        this.cards = new ArrayList<CreditCard>();
        for (int i = 0; i < 6; i++) {
            cards.add(new CreditCard());
            botoes.get(i).setTag(i+1); // assim a tag vai te o mesmo valor que o id
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
        for (CreditCard cartaoRequisitado: this.cards) {
            if (cartaoRequisitado.getId() == card.getId()) {
                cartaoRequisitado.creditValue(valor);
            }
        }
    }
    public boolean pay(CreditCard card,double valor) {
        try {
            for (CreditCard cartaoRequisitado: this.cards) {
                if (cartaoRequisitado.getId() == card.getId()) {
                    card.debitValue(valor);
                }
            }
            return true;
        } catch (ExceptionUtils e) {
            return false;
        }
    }
    public CreditCard obterCartaoPorId(int id) {
        CreditCard retornoCartao = null;
        for (CreditCard cartaoRequisitado : this.cards) {
            if(cartaoRequisitado.getId() == id) {
                retornoCartao = cartaoRequisitado;
            }
        }
        return  retornoCartao;
    }
    public Double obterSaldo(CreditCard card) {
        for (CreditCard cardDesejado: this.cards) {
            if (card.getId() == cardDesejado.getId()) {
                return cardDesejado.getSaldo();
            }
        }
        return 0.00;
    }

}
