package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Instakill extends Entity {

    public OBJ_Instakill(GamePanel gp){
        super(gp);

        bonus=true;
        type= type_pickupOnly;
        name="Instakill";
        down1=setup("/object/instakill",gp.tileSize,gp.tileSize);
    }
}
