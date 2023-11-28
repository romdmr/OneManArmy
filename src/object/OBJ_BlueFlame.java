package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueFlame extends Entity {
    GamePanel gp;
    public OBJ_BlueFlame(GamePanel gp){
        super(gp);

        name = "BlueFlame";
        down1 = setup("/object/blueFlame",gp.tileSize,gp.tileSize);
    }
}
