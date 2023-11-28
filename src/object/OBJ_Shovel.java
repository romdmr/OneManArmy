package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shovel extends Entity {
    GamePanel gp;
    public OBJ_Shovel(GamePanel gp){
        super(gp);

        name = "Shovel";
        down1 = setup("/object/shovel",gp.tileSize,gp.tileSize);
    }
}
