package curva.de.koch;

import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CurvaDeKoch extends JPanel {

    double sin60 = Math.sin(Math.PI / 3);  // Valor del seno de 60 grados
    int nivel_de_recursividad = 6;  // Nivel de recursividad

    // Constructor para inicializar el panel
    public CurvaDeKoch() { }

    // Sobreescribir el método paintComponent para dibujar
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Llama al método de la superclase para limpiar el fondo
        
        // Coordenadas del triángulo equilátero
        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) * 2 / 5;  
        int centerX = width / 2;
        int centerY = height / 2;
        
        double[] xPoints = new double[3];
        double[] yPoints = new double[3];
        
        // Cálculo de las coordenadas del triángulo equilátero con un margen adicional
        for (int i = 0; i < 3; i++) {
            xPoints[i] = centerX + size * Math.cos(i * 2 * Math.PI / 3 - Math.PI / 2);
            yPoints[i] = centerY + size * Math.sin(i * 2 * Math.PI / 3 - Math.PI / 2);
        }
        
        // Dibujar los tres lados del triángulo con recursividad
        for (int i = 0; i < 3; i++) {
            int next = (i + 1) % 3;
            paintRecursivo(g, nivel_de_recursividad, xPoints[i], yPoints[i], xPoints[next], yPoints[next]);
        }
    }

    // Método recursivo para dibujar la curva de Koch
    private void paintRecursivo(Graphics g, int nivel, double xp1, double yp1, double xp2, double yp2) {
        if (nivel == 0) {
            g.drawLine((int) xp1, (int) yp1, (int) xp2, (int) yp2);  // Dibuja una línea simple cuando la recursividad llega a 0
        } else {
            double dx = (xp2 - xp1) / 3.;  // Diferencia en x dividida en 3 partes
            double dy = (yp2 - yp1) / 3.;  // Diferencia en y dividida en 3 partes
            
            double x1 = xp1 + dx;
            double y1 = yp1 + dy;
            
            double x3 = xp2 - dx;
            double y3 = yp2 - dy;
            
            double x2 = x1 + (x3 - x1) * Math.cos(Math.PI / 3) + (y3 - y1) * Math.sin(Math.PI / 3);
            double y2 = y1 - (x3 - x1) * Math.sin(Math.PI / 3) + (y3 - y1) * Math.cos(Math.PI / 3);

            // Llamadas recursivas para subdividir la línea
            paintRecursivo(g, nivel - 1, xp1, yp1, x1, y1);  // Primera parte
            paintRecursivo(g, nivel - 1, x1, y1, x2, y2);    // Segunda parte (pico del triángulo)
            paintRecursivo(g, nivel - 1, x2, y2, x3, y3);    // Tercera parte
            paintRecursivo(g, nivel - 1, x3, y3, xp2, yp2);  // Cuarta parte
        }
    }

    public static void main(String[] args) {
        // Crear el JFrame (ventana)
        JFrame frame = new JFrame("Copo de Nieve de Koch");
        CurvaDeKoch panel = new CurvaDeKoch();  // Instanciar el panel que dibuja la curva

        // Agregar el panel a la ventana
        frame.add(panel);
        frame.setSize(800, 800);  // Establecer el tamaño de la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cerrar la ventana al salir
        frame.setVisible(true);  // Hacer visible la ventana
    }
}

