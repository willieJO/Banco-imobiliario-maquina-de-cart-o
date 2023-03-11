package br.edu.ifsp.dmos5.Model;

import br.edu.ifsp.dmos5.Utils.ExceptionUtils;

public class CreditCard {
    private int id;
    private Double saldo;
    private static int lastCardId = 0;

    public CreditCard() {
        this.setId(lastCardId + 1);
        this.setSaldo(15000.00);
        lastCardId++;
    }

    public void creditValue(Double valor) {
        setSaldo(getSaldo() + valor);
    }

    public void debitValue(Double valor) throws ExceptionUtils {
        if (valor > getSaldo()) {
            throw  new ExceptionUtils();
        }
        setSaldo(getSaldo() - valor);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
