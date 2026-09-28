package app;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

public class Janela extends JFrame {

    private JLabel jlAgencia;
    private JFormattedTextField jtfAgencia;
    private JLabel jlConta;
    private JFormattedTextField jtfConta;
    private JSeparator jSeparator01;
    private JLabel jlNome;
    private JTextField jtfNome;
    private JLabel jlEndereco;
    private JTextField jtfEndereco;
    private JLabel jlTelefone;
    private JFormattedTextField jtfTelefone;
    private JLabel jlCpf;
    private JFormattedTextField jtfCpf;
    private JRadioButton jrbCorrente;
    private JRadioButton jrbPoupanca;
    private ButtonGroup bgContas;
    private JSeparator jSeparator02;
    private JButton jbConsultar;
    private JButton jbAtualizar;
    private JButton jbFechar;

    public Janela() {
        setTitle("Laboratório de Programação");
        setSize(400, 255);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jlAgencia = new JLabel("Código da Agência:");
        jlAgencia.setSize(110, 18);
        jlAgencia.setLocation(10, 10);
        getContentPane().add(jlAgencia);

        jtfAgencia = createMaskedField("####-#");
        jtfAgencia.setSize(50, 20);
        jtfAgencia.setLocation(125, 10);
        getContentPane().add(jtfAgencia);

        jlConta = new JLabel("Número da Conta:");
        jlConta.setSize(105, 18);
        jlConta.setLocation(205, 10);
        getContentPane().add(jlConta);

        jtfConta = createMaskedField("#####-#");
        jtfConta.setSize(60, 20);
        jtfConta.setLocation(315, 10);
        getContentPane().add(jtfConta);

        jSeparator01 = new JSeparator();
        jSeparator01.setSize(365, 10);
        jSeparator01.setLocation(10, 40);
        getContentPane().add(jSeparator01);

        jlNome = new JLabel("Nome:");
        jlNome.setSize(60, 18);
        jlNome.setLocation(10, 50);
        jlNome.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlNome);

        jtfNome = new JTextField();
        jtfNome.setSize(300, 20);
        jtfNome.setLocation(75, 50);
        getContentPane().add(jtfNome);

        jlEndereco = new JLabel("Endereço:");
        jlEndereco.setSize(60, 18);
        jlEndereco.setLocation(10, 75);
        jlEndereco.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlEndereco);

        jtfEndereco = new JTextField();
        jtfEndereco.setSize(300, 20);
        jtfEndereco.setLocation(75, 75);
        getContentPane().add(jtfEndereco);

        jlTelefone = new JLabel("Telefone:");
        jlTelefone.setSize(60, 18);
        jlTelefone.setLocation(10, 100);
        jlTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlTelefone);

        jtfTelefone = createMaskedField("(##) #####-####");
        jtfTelefone.setSize(300, 20);
        jtfTelefone.setLocation(75, 100);
        getContentPane().add(jtfTelefone);

        jlCpf = new JLabel("CPF:");
        jlCpf.setSize(60, 18);
        jlCpf.setLocation(10, 125);
        jlCpf.setHorizontalAlignment(SwingConstants.RIGHT);
        getContentPane().add(jlCpf);

        jtfCpf = createMaskedField("###.###.###-##");
        jtfCpf.setSize(300, 20);
        jtfCpf.setLocation(75, 125);
        getContentPane().add(jtfCpf);

        jrbCorrente = new JRadioButton("Conta Corrente");
        jrbCorrente.setSize(111, 20);
        jrbCorrente.setLocation(100, 150);
        jrbCorrente.setMnemonic(KeyEvent.VK_C);
        jrbCorrente.setSelected(true);
        getContentPane().add(jrbCorrente);

        jrbPoupanca = new JRadioButton("Conta Poupança");
        jrbPoupanca.setSize(118, 20);
        jrbPoupanca.setLocation(225, 150);
        jrbPoupanca.setMnemonic(KeyEvent.VK_P);
        getContentPane().add(jrbPoupanca);

        bgContas = new ButtonGroup();
        bgContas.add(jrbCorrente);
        bgContas.add(jrbPoupanca);

        jSeparator02 = new JSeparator();
        jSeparator02.setSize(365, 10);
        jSeparator02.setLocation(10, 180);
        getContentPane().add(jSeparator02);

        jbConsultar = new JButton("Consultar");
        jbConsultar.setSize(100, 23);
        jbConsultar.setLocation(35, 190);
        jbConsultar.setMnemonic(KeyEvent.VK_S);
        getContentPane().add(jbConsultar);

        jbAtualizar = new JButton("Atualizar");
        jbAtualizar.setSize(100, 23);
        jbAtualizar.setLocation(145, 190);
        jbAtualizar.setMnemonic(KeyEvent.VK_A);
        jbAtualizar.setEnabled(false);
        getContentPane().add(jbAtualizar);

        // O enunciado indica x = 225, mas isso sobrepõe o botão Atualizar (145 + 100 = 245).
        // Usamos 255 para manter o espaçamento de 10 px entre os botões, como na figura.
        jbFechar = new JButton("Fechar");
        jbFechar.setSize(100, 23);
        jbFechar.setLocation(255, 190);
        jbFechar.setMnemonic(KeyEvent.VK_F);
        jbFechar.addActionListener(e -> dispose());
        getContentPane().add(jbFechar);
    }

    private JFormattedTextField createMaskedField(String pattern) {
        try {
            MaskFormatter mask = new MaskFormatter(pattern);
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }
}
