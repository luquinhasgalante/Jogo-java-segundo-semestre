import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Dino extends JPanel implements ActionListener, KeyListener{
    int largura;
    int altura;

    int velocidade = 20;

    Image personagemAndando;
    Image personagemPulando;
    Image imgOsbtaculo1;
    Image imgOsbtaculo2;
    Image personagemMorto;
    Timer loopTimer;
    Timer obsTimer;


    int alturaPersonagem;
    int larguraPersonagem;
    int personagemX;
    int personagemY;

    int alturaObs = 30;
    int larguraObs = 30;
    int obsX;
    int obsY;

    int sla = 20;

    int recorde = 0;

    int velocidadeY = 0;
    int gravidade = 2;

    Image background = Toolkit.getDefaultToolkit().createImage("ceuazul.jpeg");
    Image background2 = Toolkit.getDefaultToolkit().createImage("ceuazul2.jpeg");
    Bloco personagemBloco;

    Bloco fundo1;
    Bloco fundo2;

    ArrayList<Bloco> arrayObs;

    int score = 0;

    boolean gameOver = false;

    public Dino(Menu menu) {
        this.altura = 250;
        this.largura = 720;

        this.setFocusable(true);
        this.addKeyListener(this);

        this.alturaPersonagem = menu.personagemEscolhido.altura;
        this.larguraPersonagem = menu.personagemEscolhido.largura;
        this.personagemX = 50;
        this.personagemY = altura - alturaPersonagem;
        this.personagemY = altura - alturaPersonagem;
        this.obsX = largura;
        this.obsY = altura - alturaObs;

        arrayObs = new ArrayList<Bloco>();

        this.personagemAndando = menu.personagemEscolhido.imagem;
        this.personagemPulando = menu.personagemEscolhido.imagemPulando;
        this.imgOsbtaculo1 = new ImageIcon(getClass().getResource("/caixa.png")).getImage();
        this.imgOsbtaculo2 = new ImageIcon(getClass().getResource("/caixa2.png")).getImage();
        this.personagemMorto = menu.personagemEscolhido.imagemMorto;



        personagemBloco = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, personagemAndando);
        fundo1 = new Bloco(largura * 4, altura, 0, 0, background);
        fundo2 = new Bloco(largura * 4, altura, largura * 4, 0, background2);


        this.setPreferredSize(new Dimension(largura, altura));
        this.setSize(new Dimension(largura, altura));
        

        obsTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botarObs();
            } 
        });
        obsTimer.start();

        loopTimer = new Timer(1000/60, this);
        loopTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public boolean intersects(Bloco a, Bloco b) {
        return a.x < b.x + b.largura && a.x + a.largura > b.x && a.y < b.y + b.altura && a.y + a.altura > b.y;
    }

    public void botarObs() {
        double chance = Math.random();
            sla += 2;

        if(chance < .4) {
            Bloco obs = new Bloco(larguraObs, alturaObs, obsX, obsY, imgOsbtaculo1);
            arrayObs.add(obs);
        }
        else if(chance < .8) {
            Bloco obs = new Bloco(larguraObs, alturaObs, obsX, obsY, imgOsbtaculo2);
            arrayObs.add(obs);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(fundo1.imagem, fundo1.x, fundo1.y, fundo1.largura, fundo1.altura, null);
        g.drawImage(fundo2.imagem, fundo2.x, fundo2.y, fundo2.largura, fundo2.altura, null);
        g.drawImage(personagemBloco.imagem, personagemBloco.x, personagemBloco.y, personagemBloco.largura, personagemBloco.altura, null );

        for(int i = 0; i < arrayObs.size(); i++) {
            Bloco obs = arrayObs.get(i);
            g.drawImage(obs.imagem, obs.x, obs.y, obs.largura, obs.altura, null);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 30));
        g.drawString("Score: " + score, 30, 30);
        g.drawString("Recorde: " + recorde, 450, 30);
    }

    public void mover() {
        velocidadeY += gravidade;
        personagemBloco.y += velocidadeY;

        if(personagemBloco.y > personagemY) {
            personagemBloco.y = personagemY;
            velocidadeY = 0;
            personagemBloco.imagem = personagemAndando;
        }

        if(fundo1.x <= -largura * 4) {
            fundo1.x = fundo2.x + fundo1.largura;
        }

        if(fundo2.x <= -largura * 4) {
            fundo2.x = fundo1.x + fundo1.largura;
        }

        for(Bloco b : arrayObs) {
            b.x -= velocidade;
    
            if(intersects(personagemBloco, b)) {
                gameOver = true;
                personagemBloco.imagem = personagemMorto;
            }
        }

        fundo1.x -= velocidade;
        fundo2.x -= velocidade;

        score++;

        velocidade = sla / 2;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        mover();
        repaint();

        if(gameOver) {
            loopTimer.stop();
            obsTimer.stop();
        }
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
            if(personagemBloco.y == personagemY) {
                velocidadeY = -20;
                personagemBloco.imagem = personagemPulando;
            }

            if(gameOver) {
                gameOver = false;
                fundo1.x = 0;
                fundo2.x = fundo2.largura;
                
                recorde = Math.max(recorde, score);
                score = 0;
                arrayObs.clear();
                sla = 20;

                loopTimer.start();
                obsTimer.start();
                
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}