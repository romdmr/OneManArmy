package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_Juggernaut extends Entity{

    GamePanel gp;
    public OBJ_Juggernaut(GamePanel gp){
        super(gp);

        type = type_consumable;

        bonus=false;
        name = "Juggernaut";
        down1 = setup("/object/jugg",gp.tileSize,gp.tileSize);
        down2 = setup("/object/juggBadge",gp.tileSize,gp.tileSize);
        description = name + " - More protection !";
        price = 2500;
    }
}