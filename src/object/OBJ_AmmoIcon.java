package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_AmmoIcon extends Entity{

    public OBJ_AmmoIcon(GamePanel gp){

        super(gp);

        bonus=false;
        name="Ammo Icon";
        image1 =setup("/object/bullet_empty",gp.tileSize,gp.tileSize);
        image2 =setup("/object/bullet_full",gp.tileSize,gp.tileSize);
    }
}
