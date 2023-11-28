package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_StaminUp extends Entity{

    GamePanel gp;
    public OBJ_StaminUp(GamePanel gp){
        super(gp);

        type = type_consumable;

        bonus=false;
        name = "StaminUp";
        down1 = setup("/object/staminup",gp.tileSize,gp.tileSize);
        down2 = setup("/object/stamin_upBadge",gp.tileSize,gp.tileSize);
        description = name + " - Speed up !";
        price = 1500;
    }
}
