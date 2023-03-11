package br.edu.ifsp.dmos5;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.edu.ifsp.dmos5.Model.CreditCard;
import br.edu.ifsp.dmos5.Model.StarBank;

public class MainActivity extends AppCompatActivity {
    private Button iniciar;
    private Button jogador2;
    private Button jogador1;
    private StarBank banco;
    private Button jogador3;
    private Button jogador4;
    private Button jogador5;
    private Button jogador6;
    private Button completaRodada;
    private ArrayList<Button> listaDeJogadores;
    private AlertDialog alerta;
    private CreditCard cardAtual;
    private static final int valorAoCompletarRodada = 10000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startResources();
        StarBank.getInstance().startCreditCards(listaDeJogadores);

    }

    public void startResources(){
        // findAll
        iniciar = findViewById(R.id.iniciar);
        jogador1 = findViewById(R.id.buttonJogador1);
        jogador2 = findViewById(R.id.buttonJogador2);
        jogador3 = findViewById(R.id.buttonJogador3);
        jogador4 = findViewById(R.id.buttonJogador4);
        jogador5 = findViewById(R.id.buttonJogador5);
        jogador6 = findViewById(R.id.buttonJogador6);
        completaRodada = findViewById(R.id.completaRodada);
        // atribui os onClick
        iniciar.setOnClickListener(view -> jogadoresVisiveis());
        jogador1.setOnClickListener(view -> gerarOpcaoJogador(jogador1));
        jogador2.setOnClickListener(view -> gerarOpcaoJogador(jogador2));
        jogador3.setOnClickListener(view -> gerarOpcaoJogador(jogador3));
        jogador4.setOnClickListener(view -> gerarOpcaoJogador(jogador4));
        jogador5.setOnClickListener(view -> gerarOpcaoJogador(jogador5));
        jogador6.setOnClickListener(view -> gerarOpcaoJogador(jogador6));
        completaRodada.setOnClickListener(view -> completaRodada());
        // Cria a lista de jogadores e adiciona
        listaDeJogadores = new ArrayList<Button>();
        listaDeJogadores.add(jogador1);
        listaDeJogadores.add(jogador2);
        listaDeJogadores.add(jogador3);
        listaDeJogadores.add(jogador4);
        listaDeJogadores.add(jogador5);
        listaDeJogadores.add(jogador6);
        // inicializa o Banco de fato
        banco = new StarBank();
        banco.startCreditCards(listaDeJogadores);
        // ele vai simular o cartão escolhido ao aperta no botão
        cardAtual = new CreditCard();
    }

    public void jogadoresVisiveis() {
        for (Button jogador: listaDeJogadores) {
            jogador.setVisibility(View.VISIBLE);
        }
        completaRodada.setVisibility(View.VISIBLE);
    }

    public void gerarOpcaoJogador (Button jogador) {
        /*
        Foi criado uma tag quando é iniciado os cartão banco.startCreditCards()
        para que eu consiga acessar os cartão, ao qual a tag vai te o mesmo valor que o Id
        assim, consigo percorrer a minah lista de cartão que só o banco tem acesso
        e envio da minha tela pro banco um cartão que vai possui o mesmo Id que um cartão
        que o banco possui;
        */

        int id;
        try {
            id = Integer.parseInt(jogador.getTag().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Erro no id",Toast.LENGTH_SHORT);
            return;
        }
        cardAtual.setId(id);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escolha uma opção:");

        builder.setPositiveButton("Receber", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                modalBuilder.setTitle("Digite o valor a receber:");
                modalBuilder.setCancelable(false);
                // Adiciona um EditText no modal
                final EditText input = new EditText(MainActivity.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                modalBuilder.setView(input);
                // Adiciona um botão "enviar" no modal
                modalBuilder.setPositiveButton("Receber valor do banco", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        double valorDigitado = obterValor(input.getText().toString());
                        banco.recive(cardAtual,valorDigitado);
                    }
                });

                modalBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                modalBuilder.show();
            }
        });

// Adiciona um botão "Pagar" no AlertDialog
        builder.setNegativeButton("Pagar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                modalBuilder.setTitle("Opções de pagamento");
                modalBuilder.setCancelable(false);
                modalBuilder.setPositiveButton("Banco", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                        modalBuilder.setTitle("Digite o valor a pagar ao banco:");
                        modalBuilder.setCancelable(false);
                        final EditText input = new EditText(MainActivity.this);
                        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                        modalBuilder.setView(input);
                        modalBuilder.setNegativeButton("Pagar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                double valor = obterValor(input.getText().toString());
                                boolean retornoAcao = banco.pay(cardAtual,valor);
                                final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                                String titleText = retornoAcao ? "Ação realizada com sucesso" : "Falha na ação, jogador não possui esse valor";
                                modalBuilder.setTitle(titleText);
                                modalBuilder.setCancelable(false);
                                modalBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                modalBuilder.show();
                            }
                        });
                        modalBuilder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        modalBuilder.show();

                    }
                });
                modalBuilder.setNegativeButton("Jogador", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                        final EditText inputNumeroCartao = new EditText(MainActivity.this);
                        final EditText inputValorTransferencia = new EditText(MainActivity.this);
                        modalBuilder.setTitle("Transferencia para jogador");
                        modalBuilder.setCancelable(false);
                        // Configurações do campo de entrada para número do cartão
                        inputNumeroCartao.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        // Configurações do campo de entrada para o valor da transferência
                        inputValorTransferencia.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                        // Criação do layout personalizado com os títulos e campos de entrada
                        LinearLayout layout = new LinearLayout(MainActivity.this);
                        layout.setOrientation(LinearLayout.VERTICAL);

                        TextView textNumeroCartao = new TextView(MainActivity.this);
                        textNumeroCartao.setText("Digite o número do cartão:");
                        textNumeroCartao.setGravity(Gravity.CENTER);
                        layout.addView(textNumeroCartao);
                        layout.addView(inputNumeroCartao);

                        TextView textValorTransferencia = new TextView(MainActivity.this);
                        textValorTransferencia.setText("Valor da transferência:");
                        textValorTransferencia.setGravity(Gravity.CENTER);
                        layout.addView(textValorTransferencia);
                        layout.addView(inputValorTransferencia);

                        modalBuilder.setView(layout);
                        modalBuilder.setNegativeButton("Enviar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CreditCard cartaoReceptor = new CreditCard();
                                int idReceptor;
                                double valor;
                                try {
                                    idReceptor = Integer.parseInt(inputNumeroCartao.getText().toString());
                                    valor = Double.parseDouble(inputValorTransferencia.getText().toString());
                                    if (idReceptor == cardAtual.getId()) {
                                        dialogInterface.cancel();
                                        final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                                        modalBuilder.setTitle("Você não pode transferir para você mesmo");
                                        modalBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        modalBuilder.show();
                                    } else {
                                        cartaoReceptor.setId(idReceptor);
                                        banco.transfer(cardAtual,cartaoReceptor,valor);
                                        final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                                        modalBuilder.setTitle("Transferencia realizada com sucesso");
                                        modalBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dialogInterface.cancel();
                                            }
                                        });
                                        modalBuilder.show();
                                    }

                                } catch (NumberFormatException e) {
                                    Toast.makeText(MainActivity.this,"Erro nos valores digitados",Toast.LENGTH_SHORT);
                                    dialogInterface.cancel();
                                }

                            }
                        });
                        modalBuilder.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        modalBuilder.show();
                    }
                });
                modalBuilder.show();
            }
        });

// Adiciona um botão "Visualizar" no AlertDialog
        builder.setNeutralButton("Visualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                modalBuilder.setTitle("Informações do jogador");

                // Crie um novo EditText e adicione o atributo TextView e a fonte em negrito
                TextView editText = new TextView(MainActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setTypeface(null, Typeface.BOLD);
                editText.setText(banco.obterSaldo(cardAtual).toString());
                editText.setTextSize(20);
                // Crie um novo LinearLayout e adicione o EditText a ele
                LinearLayout linearLayout = new LinearLayout(MainActivity.this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(0,20,0,0);
                linearLayout.setGravity(Gravity.CENTER);
                linearLayout.addView(editText);

                // Adicione o LinearLayout ao modal usando o método setView()
                modalBuilder.setView(linearLayout);

                modalBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                modalBuilder.setCancelable(false);
                modalBuilder.show();
            }
        });

        builder.show();
    }

    public double obterValor(String valor) {
        try {
            return Double.parseDouble(valor.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    public void completaRodada() {
        AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
        modalBuilder.setTitle("Coloque o numero do cartão");
        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        modalBuilder.setView(input);
        modalBuilder.setPositiveButton("Completar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String id = input.getText().toString();
                
                try {
                    CreditCard cartao = new CreditCard();
                    cartao.setId(Integer.parseInt(id));
                    boolean retornoRound =  banco.roundCompleted(cartao,valorAoCompletarRodada);
                    if (retornoRound) {
                        final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                        modalBuilder.setTitle("Transferencia realizada com sucesso");
                        modalBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        modalBuilder.show();
                    } else {
                        final AlertDialog.Builder modalBuilder = new AlertDialog.Builder(MainActivity.this);
                        modalBuilder.setTitle("Erro, verifique o numero informado");
                        modalBuilder.setPositiveButton("Fechar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        modalBuilder.show();
                    }


                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this,"Numero invalido",Toast.LENGTH_SHORT);
                }
                
                
                dialogInterface.cancel();
            }
        });
        modalBuilder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        modalBuilder.show();
    }

}