import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Menu extends JPanel implements ActionListener, KeyListener {
    int altura = 250;
    int largura = 720;

    int contador = 0;

    int alturaPersonagem = 50;
    int larguraPersonagem = 50;

    int personagemX = 335;
    int personagemY;

    Image fundo = new ImageIcon(getClass().getResource("fundo.jpg")).getImage();
    Image fundoDireita = new ImageIcon(getClass().getResource("fundo direita.jpg")).getImage();
    Image fundoEsquerda = new ImageIcon(getClass().getResource("fundo esquerda.jpg")).getImage();
    Bloco background = new Bloco(largura, altura, 0, 0, fundo);
    
    ArrayList<Bloco> fundos;
    ArrayList<Bloco> personagens;

    Bloco personagemEscolhido;
    Bloco blocoPadrao;
    Bloco blocoDino;
    Bloco blocoMegaman;
    Bloco blocoPikachu;

    Timer loopTimer;


    public Menu() {
        
        this.setSize(new Dimension(largura, altura));
        this.setPreferredSize(new Dimension(largura, altura));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);


        loopTimer = new Timer(1000/60, this);
        loopTimer.start();

        this.personagemY = (altura / 2) - 10;


        Image padrao = new ImageIcon(getClass().getResource("andar.gif")).getImage();
        Image padraoPulando = new ImageIcon(getClass().getResource("pulando.png")).getImage();
        Image padraoMorto = new ImageIcon(getClass().getResource("mortinho.png")).getImage();


        Image dino = new ImageIcon(getClass().getResource("dino-run.gif")).getImage();
        Image dinoPulo = new ImageIcon(getClass().getResource("dino.png")).getImage();
        Image dinoMorto = new ImageIcon(getClass().getResource("dino-dead.png")).getImage();

        Image megaman = new ImageIcon("megaman-right-walk.gif").getImage();
        Image megamanPulo = new ImageIcon("megaman-right-jump.png").getImage();
        Image megamanMorto = new ImageIcon("megaman-right-jump.png").getImage();

        Image pikachu = new ImageIcon("pikachu.gif").getImage();        
        Image pikachuPulo = new ImageIcon("pikachupulo.png").getImage();
        Image pikachuMorto = new ImageIcon("pikachudeath.png").getImage();

        
        blocoPadrao = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, padrao, padraoPulando, padraoMorto);
        blocoDino = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, dino, dinoPulo, dinoMorto);
        blocoMegaman = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, megaman, megamanPulo, megamanMorto);
        blocoPikachu = new Bloco(60, 60, personagemX, personagemY, pikachu, pikachuPulo, pikachuMorto);


        personagens = new ArrayList<>();

        personagens.add(blocoPadrao);
        personagens.add(blocoDino);
        personagens.add(blocoMegaman);
        personagens.add(blocoPikachu);

        personagemEscolhido = personagens.get(contador);
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(background.imagem, background.x, background.y, background.largura, background.altura + 60, null);
        g.drawImage(personagemEscolhido.imagem, personagemEscolhido.x, personagemEscolhido.y, personagemEscolhido.largura, personagemEscolhido.altura, null);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        JFrame frame = new JFrame();
        Dino dino = new Dino (this);

        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {

            background.imagem = fundoDireita;

            if(contador < personagens.size() - 1){
                contador++;
                personagemEscolhido = personagens.get(contador);
            }
            else{
                personagemEscolhido = personagens.get(0);
                contador = 0;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            background.imagem = fundoEsquerda;

            if(contador > 0) {
                contador--;
                personagemEscolhido = personagens.get(contador);
            }else{
                contador = (personagens.size() - 1);
                personagemEscolhido = personagens.get(personagens.size() - 1);
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_SPACE) {
            int largura = 720;
            int altura = 250;

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(largura, altura);
            frame.setLayout(new BorderLayout());
            frame.setResizable(false);

            frame.add(dino);
            frame.setLocationRelativeTo(null);
            
            dino.requestFocus();
            frame.pack();
            frame.setVisible(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        background.imagem = fundo;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
