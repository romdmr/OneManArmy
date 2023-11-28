package object;

import entity.Projectile;
import main.GamePanel;

public class OBJ_Tomahawk extends Projectile {

    GamePanel gp;
    public OBJ_Tomahawk(GamePanel gp) {
        super(gp);
        this.gp=gp;

        type = type_consumable;

        solidArea.x = 5;
        solidArea.y = 5;
        solidArea.width = 38;
        solidArea.height = 38;
        bonus=false;
        name="Tomahawk";
        speed=4;
        maxLife=100;
        life=maxLife;
        attack=99;
        alive=false;
        getImage();
    }

    public void getImage(){
        up1 = setup("/projectile/tomahawk_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/projectile/tomahawk_up2",gp.tileSize,gp.tileSize);
        up3 = setup("/projectile/tomahawk_up3",gp.tileSize,gp.tileSize);
        //up4 = setup("/projectile/tomahawk_up4",gp.tileSize,gp.tileSize);
        down1 = setup("/projectile/tomahawk_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/projectile/tomahawk_down2",gp.tileSize,gp.tileSize);
        down3 = setup("/projectile/tomahawk_down3",gp.tileSize,gp.tileSize);
        //down4 = setup("/projectile/tomahawk_down4",gp.tileSize,gp.tileSize);
        left1 = setup("/projectile/tomahawk_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/projectile/tomahawk_left2",gp.tileSize,gp.tileSize);
        left3 = setup("/projectile/tomahawk_left3",gp.tileSize,gp.tileSize);
        //left4 = setup("/projectile/tomahawk_left4",gp.tileSize,gp.tileSize);
        right1 = setup("/projectile/tomahawk_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/projectile/tomahawk_right2",gp.tileSize,gp.tileSize);
        right3 = setup("/projectile/tomahawk_right3",gp.tileSize,gp.tileSize);
        //right4 = setup("/projectile/tomahawk_right4",gp.tileSize,gp.tileSize);
    }
}
