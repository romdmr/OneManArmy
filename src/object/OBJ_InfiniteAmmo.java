package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_InfiniteAmmo extends Entity {

    public OBJ_InfiniteAmmo(GamePanel gp){
        super(gp);

        bonus=true;
        type= type_pickupOnly;
        name="Infinite Ammo";
        down1=setup("/object/infinite_ammo",gp.tileSize,gp.tileSize);
    }
}
