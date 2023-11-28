package object;

import entity.Projectile;
import entity.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class OBJ_BasicBullet extends Projectile {

    GamePanel gp;
    public OBJ_BasicBullet(GamePanel gp) {
        super(gp);
        this.gp=gp;

        type = type_consumable;

        bonus=false;
        speed=10;
        maxLife=80;
        life=maxLife;
        attack=3;
        alive=false;
        getImage();
    }

    public void getImage(){
        up1 = setup("/projectile/basicBullet_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/basicBullet_up2",gp.tileSize,gp.tileSize);
        up3 = setup("/projectile/basicBullet_up3",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/basicBullet_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/basicBullet_down2",gp.tileSize,gp.tileSize);
        down3 = setup("/projectile/basicBullet_down3",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/basicBullet_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/basicBullet_left2",gp.tileSize,gp.tileSize);
        left3 = setup("/projectile/basicBullet_left3",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/basicBullet_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/basicBullet_right2",gp.tileSize,gp.tileSize);
        right3 = setup("/projectile/basicBullet_right3",gp.tileSize,gp.tileSize);

        //Pack-a-punched sprites
        paped_up1 = setup("/projectile/pap_bullet_up_1",gp.tileSize,gp.tileSize);
        paped_up2 = setup("/projectile/pap_bullet_up_2",gp.tileSize,gp.tileSize);
        paped_up3 = setup("/projectile/pap_bullet_up_2",gp.tileSize,gp.tileSize);
        paped_down1 = setup("/projectile/pap_bullet_down_1",gp.tileSize,gp.tileSize);
        paped_down2 = setup("/projectile/pap_bullet_down_2",gp.tileSize,gp.tileSize);
        paped_down3 = setup("/projectile/pap_bullet_down_3",gp.tileSize,gp.tileSize);
        paped_left1 = setup("/projectile/pap_bullet_left_1",gp.tileSize,gp.tileSize);
        paped_left2 = setup("/projectile/pap_bullet_left_2",gp.tileSize,gp.tileSize);
        paped_left3 = setup("/projectile/pap_bullet_left_3",gp.tileSize,gp.tileSize);
        paped_right1 = setup("/projectile/pap_bullet_right_1",gp.tileSize,gp.tileSize);
        paped_right2 = setup("/projectile/pap_bullet_right_2",gp.tileSize,gp.tileSize);
        paped_right3 = setup("/projectile/pap_bullet_right_3",gp.tileSize,gp.tileSize);
    }

    public boolean haveRessource(Entity user){
        boolean haveRessource=false;
        if(user.ammo>=1){
            haveRessource=true;
        }
        return haveRessource;
    }

    public void substractRessource(Entity user){
        user.ammo-=1;
        if(user.infiniteAmmoIsOn){
            user.ammo++;
        }
    }
}
