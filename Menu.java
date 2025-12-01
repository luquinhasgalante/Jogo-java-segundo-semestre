import java.awt.BorderLayout;
import java.awt.Dimension;
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

    int contadorX = 0;
    int contadorY = 0;

    int alturaPersonagem = 50;
    int larguraPersonagem = 50;

    int personagemX = 335;
    int personagemY;

    Image fundo = new ImageIcon(getClass().getResource("img/fundo.png")).getImage();
    Image fundoDireita = new ImageIcon(getClass().getResource("img/fundo direita.png")).getImage();
    Image fundoEsquerda = new ImageIcon(getClass().getResource("img/fundo esquerda.png")).getImage();
    Image fundoCima = new ImageIcon(getClass().getResource("img/fundo cima.png")).getImage();
    Image fundoBaixo = new ImageIcon(getClass().getResource("img/fundo baixo.png")).getImage();
    Bloco background = new Bloco(largura, altura, 0, 0, fundo);
    
    ArrayList<Bloco> fundos;
    ArrayList<Bloco> fundosPreview;
    ArrayList<Bloco> personagens;

    Bloco fundoEscolhido;
    Bloco fundoFloresta;
    Bloco fundoCanyon;
    Bloco fundoMontanha;

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

        
        Image florestaPreview = new ImageIcon(getClass().getResource("img/background_preview_1.jpg")).getImage();
        Image montanhaPreview = new ImageIcon(getClass().getResource("img/background_preview_2.png")).getImage();
        Image canyonPreview = new ImageIcon(getClass().getResource("img/background_preview_3.png")).getImage();
        
        Image floresta = new ImageIcon(getClass().getResource("img/background_layer_1.png")).getImage();
        Image floresta1 = new ImageIcon(getClass().getResource("img/background_layer_2.png")).getImage();
        Image floresta2 = new ImageIcon(getClass().getResource("img/background_layer_3.png")).getImage();
        
        Image canyon = new ImageIcon(getClass().getResource("img/sky.png")).getImage();
        Image canyon1 = new ImageIcon(getClass().getResource("img/far-mountains.png")).getImage();
        Image canyon2 = new ImageIcon(getClass().getResource("img/canyon.png")).getImage();
        
        Image montanha = new ImageIcon(getClass().getResource("img/fundo sem lua.png")).getImage();
        Image montanha1 = new ImageIcon(getClass().getResource("img/mountains.png")).getImage();
        Image montanha2 = new ImageIcon(getClass().getResource("img/trees.png")).getImage();
        

        fundos = new ArrayList<>();

        fundoCanyon = new Bloco(largura, altura, 0, 0, canyon, canyon1, canyon2, canyonPreview);
        fundoFloresta = new Bloco(largura, altura, 0, 0, floresta, floresta1, floresta2, florestaPreview);
        fundoMontanha = new Bloco(largura, altura, 0, 0, montanha, montanha1, montanha2, montanhaPreview);
        
        fundos.add(fundoFloresta);
        fundos.add(fundoMontanha);
        fundos.add(fundoCanyon);

        fundoEscolhido = fundos.get(contadorY);


        Image padrao = new ImageIcon(getClass().getResource("img/andar.gif")).getImage();
        Image padraoPulando = new ImageIcon(getClass().getResource("img/pulando.png")).getImage();
        Image padraoMorto = new ImageIcon(getClass().getResource("img/mortinho.png")).getImage();


        Image dino = new ImageIcon(getClass().getResource("img/dino-run.gif")).getImage();
        Image dinoPulo = new ImageIcon(getClass().getResource("img/dino.png")).getImage();
        Image dinoMorto = new ImageIcon(getClass().getResource("img/dino-dead.png")).getImage();

        Image megaman = new ImageIcon("img/megaman-right-walk.gif").getImage();
        Image megamanPulo = new ImageIcon("img/megaman-right-jump.png").getImage();
        Image megamanMorto = new ImageIcon("img/megaman-right-jump.png").getImage();

        Image pikachu = new ImageIcon("img/pikachu.gif").getImage();        
        Image pikachuPulo = new ImageIcon("img/pikachupulo.png").getImage();
        Image pikachuMorto = new ImageIcon("img/pikachudeath.png").getImage();

        
        blocoPadrao = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, padrao, padraoPulando, padraoMorto);
        blocoDino = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, dino, dinoPulo, dinoMorto);
        blocoMegaman = new Bloco(larguraPersonagem, alturaPersonagem, personagemX, personagemY, megaman, megamanPulo, megamanMorto);
        blocoPikachu = new Bloco(60, 60, personagemX, personagemY, pikachu, pikachuPulo, pikachuMorto);


        personagens = new ArrayList<>();

        personagens.add(blocoPadrao);
        personagens.add(blocoDino);
        personagens.add(blocoMegaman);
        personagens.add(blocoPikachu);

        personagemEscolhido = personagens.get(contadorX);
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(background.imagem, background.x, background.y, background.largura, background.altura, null);
        g.drawImage(personagemEscolhido.imagem, 152, personagemEscolhido.y - 10, personagemEscolhido.largura, personagemEscolhido.altura, null);
        g.drawImage(fundoEscolhido.preview, largura - 195, personagemEscolhido.y - 12, 50, 50, null);

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

            if(contadorX < personagens.size() - 1){
                contadorX++;
                personagemEscolhido = personagens.get(contadorX);
            }
            else{
                personagemEscolhido = personagens.get(0);
                contadorX = 0;
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            background.imagem = fundoEsquerda;

            if(contadorX > 0) {
                contadorX--;
                personagemEscolhido = personagens.get(contadorX);
            }else{
                contadorX = (personagens.size() - 1);
                personagemEscolhido = personagens.get(personagens.size() - 1);
            }
        }


        if(e.getKeyCode() == KeyEvent.VK_UP) {
            background.imagem = fundoCima;

            if(contadorY < fundos.size() - 1) {
                contadorY++;
            } else {
                contadorY = 0;
            }

            fundoEscolhido = fundos.get(contadorY);
        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            background.imagem = fundoBaixo;

            if(contadorY > 0) {
                contadorY--;
            } else {
                contadorY = (fundos.size() - 1);
            }
            
            fundoEscolhido = fundos.get(contadorY);
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

