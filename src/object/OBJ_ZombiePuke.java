package object;

import entity.Projectile;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_ZombiePuke extends Projectile {

    GamePanel gp;
    public OBJ_ZombiePuke(GamePanel gp) {
        super(gp);
        this.gp=gp;

        type = type_consumable;

        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.width = 38;
        solidArea.height = 38;

        bonus=false;
        name="Zombie Puke";
        speed=8;
        maxLife=100;
        life=maxLife;
        attack=3;
        alive=false;
        getImage();
    }

    public void getImage(){
        up1 = setup("/projectile/zombiePuke_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/zombiePuke_up2",gp.tileSize,gp.tileSize);
        up3 = setup("/projectile/zombiePuke_up3",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/zombiePuke_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/zombiePuke_down2",gp.tileSize,gp.tileSize);
        down3 = setup("/projectile/zombiePuke_down3",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/zombiePuke_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/zombiePuke_left2",gp.tileSize,gp.tileSize);
        left3 = setup("/projectile/zombiePuke_left3",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/zombiePuke_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/zombiePuke_right2",gp.tileSize,gp.tileSize);
        right3 = setup("/projectile/zombiePuke_right3",gp.tileSize,gp.tileSize);
    }
}
