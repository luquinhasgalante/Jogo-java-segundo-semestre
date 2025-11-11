import java.awt.Image;

public class Bloco {
    int altura, largura;
    int x, y;
    Image imagem;
    Image imagemPulando;
    Image imagemMorto;

    
    public Bloco(int largura, int altura, int x, int y, Image imagem, Image imagemPulando, Image imagemMorto) {
        this.largura = largura;
        this.altura = altura;
        this.x = x;
        this.y = y;
        this.imagem = imagem;
        this.imagemPulando = imagemPulando;
        this.imagemMorto = imagemMorto;
    }

    public Bloco(int largura, int altura, int x, int y, Image imagem) {
        this.largura = largura;
        this.altura = altura;
        this.x = x;
        this.y = y;
        this.imagem = imagem;
    }
}
