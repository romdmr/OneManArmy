package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_QuickRevive extends Entity{

    GamePanel gp;
    public OBJ_QuickRevive(GamePanel gp){
        super(gp);

        type = type_consumable;

        bonus = false;
        name = "QuickRevive";
        down1 = setup("/object/quick_revive",gp.tileSize,gp.tileSize);
        down2 = setup("/object/quick_reviveBadge",gp.tileSize,gp.tileSize);
        description = name + " - More protection !";
        price = 2000;
    }
}