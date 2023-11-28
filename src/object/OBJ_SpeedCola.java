package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_SpeedCola extends Entity {

    GamePanel gp;
    public OBJ_SpeedCola(GamePanel gp){
        super(gp);

        type = type_consumable;

        bonus = false;
        name = "SpeedCola";
        down1 = setup("/object/speed_cola",gp.tileSize,gp.tileSize);
        down2 = setup("/object/speed_colaBadge",gp.tileSize,gp.tileSize);
        description = name + " - Reload faster !";
        price = 2000;
    }
}
