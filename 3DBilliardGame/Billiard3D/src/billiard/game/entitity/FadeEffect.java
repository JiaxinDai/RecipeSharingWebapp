package billiard.game.entitity;

import java.awt.Color;
import java.awt.Graphics2D;

import billiard.game.BilliardEntity;
import billiard.game.BilliardScene;
import billiard.game.infra.Engine;
import billiard.game.infra.Time;
import billiard.renderer3d.core.Renderer;

/**
 * FadeEffect class.
 * 
 */
public class FadeEffect extends BilliardEntity {

    private final Color[] colors = new Color[256];
    private Color color;
    
    private double alpha = 0;
    private double targetAlpha = 0;
    
    public FadeEffect(String name, BilliardScene scene) {
        super(name, scene);
    }

    @Override
    public void init() throws Exception {
        for (int c = 0; c < colors.length; c++) {
            colors[c] = new Color(0, 0, 0, c);
        }
        color = colors[255];
    }

    @Override
    public void update(Renderer renderer) {
        double dif = targetAlpha - alpha;
        double s = dif > 0 ? 1 : -1;
        if (dif > 0.01 || dif < -0.01) {
            double delta = Time.delta;
            delta = delta > 1 ? 1 : delta;
            alpha = alpha + s * delta * 0.02;
            alpha = alpha > 1 ? 1 : alpha;
        }
        else {
            alpha = targetAlpha;
        }
        color = colors[(int) (255 * alpha)];
    }
    
    @Override
    public void draw(Renderer renderer, Graphics2D g) {
        g.setColor(color);
        g.fillRect(0, 0, Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT);
    }
    
    // broadcast messages

    @Override
    public void stateChanged() {
        super.stateChanged();
    }
    
    public void fadeIn() {
        targetAlpha = 0;
    }

    public void fadeOut() {
        targetAlpha = 1;
    }
    
    // properties
    
    public boolean fadeEffectFinished() {
        return alpha == targetAlpha;
    }
    
}
