package br.edu.ifsp.dmos5.Model;

import java.util.ArrayList;
import java.util.Random;

import br.edu.ifsp.dmos5.Model.Utils.ExceptionUtils;

public class CreditCard {
    private int id;
    private Double saldo;
    private static int lastCardId = 0;

    public CreditCard() {
        this.id = lastCardId + 1;
        this.saldo = 15000.00;
        lastCardId++;
    }

    public void creditValue(Double valor) {
        saldo += saldo + valor;
    }

    public void debitValue(Double valor) {
        try {
            if (valor > saldo) {
                throw  new ExceptionUtils();
            }
            saldo = saldo - valor;

        } catch (ExceptionUtils ex) {
            saldo = saldo;
        }
    }

}
