package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_AxeBlade extends Entity {
    GamePanel gp;
    public OBJ_AxeBlade(GamePanel gp){
        super(gp);

        name = "AxeBlade";
        down1 = setup("/object/axeBlade",gp.tileSize,gp.tileSize);
    }
}
