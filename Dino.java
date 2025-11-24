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
import javax.swing.JPanel;
import javax.swing.Timer;

public class Dino extends JPanel implements ActionListener, KeyListener{
    int largura;
    int altura;

    int velocidade = 20;

    Image personagemAndando;
    Image personagemPulando;
    Image imgObstaculo1;
    Image imgObstaculo2;
    Image imgObstaculo3;
    Image imgObstaculo4;
    Image imgObstaculo5;
    Image personagemMorto;
    Image placar;
    Image placarRecorde;
    Image reiniciar;
    Timer loopTimer;
    Timer obsTimer;
    Timer velTimer;


    int alturaPersonagem;
    int larguraPersonagem;
    int personagemX;
    int personagemY;

    int alturaObs = 30;
    int larguraObs = 30;
    int obsX;
    int obsY;

    int recorde = 0;

    int velocidadeY = 0;
    int gravidade = 2;

    Bloco personagemBloco;

    Bloco fundo1;
    Bloco fundo2;
    Bloco fundo_1;
    Bloco fundo2_1;
    Bloco fundo_2;
    Bloco fundo2_2;


    Bloco placarBloco;
    Bloco placarRecordeBloco;

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
        this.imgObstaculo1 = new ImageIcon(getClass().getResource("img/spike A.png")).getImage();
        this.imgObstaculo2 = new ImageIcon(getClass().getResource("img/spike B.png")).getImage();
        this.imgObstaculo3 = new ImageIcon(getClass().getResource("img/spike C.png")).getImage();
        this.imgObstaculo4 = new ImageIcon(getClass().getResource("img/spike D.png")).getImage();
        this.imgObstaculo5 = new ImageIcon(getClass().getResource("img/lanca.png")).getImage();
        this.placar = new ImageIcon(getClass().getResource("img/pontos.png")).getImage();
        this.placarRecorde = new ImageIcon(getClass().getResource("img/recorde.png")).getImage();
        this.reiniciar = new ImageIcon(getClass().getResource("img/gameover.png")).getImage();
        this.personagemMorto = menu.personagemEscolhido.imagemMorto;

        
        Image background = menu.fundoEscolhido.imagem;
        Image background_1 = menu.fundoEscolhido.imagemPulando;
        Image background_2 = menu.fundoEscolhido.imagemMorto;


        personagemBloco = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, personagemAndando);
        fundo1 = new Bloco(largura, altura, 0, 0, background);
        fundo2 = new Bloco(largura, altura, fundo1.largura, 0, background);
        fundo_1 = new Bloco(largura, altura, 0, 0, background_1);
        fundo2_1 = new Bloco(largura, altura, fundo_1.largura, 0, background_1);
        fundo_2 = new Bloco(largura, altura, 0, 0, background_2);
        fundo2_2 = new Bloco(largura, altura, fundo_2.largura, 0, background_2);

        placarRecordeBloco = new Bloco(130, 25, 400, 10, placarRecorde);
        placarBloco = new Bloco(150, 25, 30, 10, placar);


        this.setPreferredSize(new Dimension(largura, altura));
        this.setSize(new Dimension(largura, altura));
        

        velTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                velocidade++;
            }
        });
        velTimer.start();

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
        
        if(chance < .2) {
            Bloco obs = new Bloco(larguraObs, alturaObs, obsX, obsY, imgObstaculo1);
            arrayObs.add(obs);
        }
        else if(chance < .4) {
            Bloco obs = new Bloco(larguraObs, alturaObs, obsX, obsY, imgObstaculo2);
            arrayObs.add(obs);
        }
        else if(chance < .6) {
            Bloco obs = new Bloco(larguraObs, alturaObs, obsX, obsY, imgObstaculo3);
            arrayObs.add(obs);
        }
        else if(chance < .8) {
            Bloco obs = new Bloco(larguraObs, alturaObs, obsX, obsY, imgObstaculo4);
            arrayObs.add(obs);
        }else{
            Bloco obs = new Bloco(larguraObs + 20, alturaObs, obsX, obsY - 36, imgObstaculo5);
            arrayObs.add(obs);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(fundo1.imagem, fundo1.x, fundo1.y, fundo1.largura, fundo1.altura, null);
        g.drawImage(fundo2.imagem, fundo2.x, fundo2.y, fundo2.largura, fundo2.altura, null);
        g.drawImage(fundo_1.imagem, fundo_1.x, fundo_1.y, fundo_1.largura, fundo_1.altura, null);
        g.drawImage(fundo2_1.imagem, fundo2_1.x, fundo2_1.y, fundo2_1.largura, fundo2_1.altura, null);
        g.drawImage(fundo_2.imagem, fundo_2.x, fundo_2.y, fundo_2.largura, fundo_2.altura, null);
        g.drawImage(fundo2_2.imagem, fundo2_2.x, fundo2_2.y, fundo2_2.largura, fundo2_2.altura, null);
        g.drawImage(personagemBloco.imagem, personagemBloco.x, personagemBloco.y, personagemBloco.largura, personagemBloco.altura, null );

        for(int i = 0; i < arrayObs.size(); i++) {
            Bloco obs = arrayObs.get(i);
            g.drawImage(obs.imagem, obs.x, obs.y, obs.largura, obs.altura, null);
        }

        
        if(gameOver) {
            g.drawImage(reiniciar, 0, 0, largura, altura, null);
        }else {
            g.setColor(Color.WHITE);
            g.setFont(new Font(Font.DIALOG, Font.BOLD, 20));
            g.drawImage(placarBloco.imagem, placarBloco.x, placarBloco.y, placarBloco.largura, placarBloco.altura, null);
            g.drawString(" " + score, 180, 33);
            g.drawImage(placarRecordeBloco.imagem, placarRecordeBloco.x, placarRecordeBloco.y, placarRecordeBloco.largura, placarRecordeBloco.altura, null);
            g.drawString("" + recorde, 550, 33);
        }
    }

    public void mover() {
        velocidadeY += gravidade;
        personagemBloco.y += velocidadeY;

        if(personagemBloco.y > (altura - personagemBloco.altura)) {
            personagemBloco.y = (altura - personagemBloco.altura);
            velocidadeY = 0;
            personagemBloco.imagem = personagemAndando;
        }

        if(fundo1.x <= - largura) {
            fundo1.x = fundo2.x + fundo1.largura;
        }

        if(fundo2.x <= - largura) {
            fundo2.x = fundo1.x + fundo1.largura;
        }

        if(fundo_1.x <= -largura) {
            fundo_1.x = fundo2_1.x + fundo_1.largura;
        }

        if(fundo2_1.x <= -largura) {
            fundo2_1.x = fundo_1.x + fundo_1.largura;
        }

        if(fundo_2.x <= -largura) {
            fundo_2.x = fundo2_2.x + fundo2_2.largura;
        }
        
        if(fundo2_2.x <= -largura) {
            fundo2_2.x = fundo_2.x + fundo_2.largura;
        }

        for(Bloco b : arrayObs) {
            b.x -= velocidade;
    
            if(intersects(personagemBloco, b)) {
                gameOver = true;
                personagemBloco.imagem = personagemMorto;
            }
        }

        fundo1.x -= (velocidade - 5);
        fundo2.x -= (velocidade - 5);

        fundo_1.x -= (velocidade - 3);
        fundo2_1.x -= (velocidade - 3);

        fundo_2.x -= velocidade;
        fundo2_2.x -= velocidade;

        score++;
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

                fundo_2.x = 0;
                fundo2_2.x = fundo_2.largura;
                
                recorde = Math.max(recorde, score);
                score = 0;
                arrayObs.clear();
                velocidade = 20;

                loopTimer.start();
                obsTimer.start();
                
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            if(personagemBloco.y == (altura - personagemBloco.altura)) {
                personagemBloco.altura = 35;
                personagemBloco.y = (altura - personagemBloco.altura);
            }
            else {
                gravidade = 10;
            }
            
            if(gameOver) {
                gameOver = false;
                fundo1.x = 0;
                fundo2.x = fundo2.largura;

                fundo_2.x = 0;
                fundo2_2.x = fundo_2.largura;
                
                recorde = Math.max(recorde, score);
                score = 0;
                arrayObs.clear();
                velocidade = 20;

                loopTimer.start();
                obsTimer.start();
                
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        personagemBloco.altura = alturaPersonagem;
        gravidade = 2;
    }

}
