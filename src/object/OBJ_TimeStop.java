package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_TimeStop extends Entity {

    public OBJ_TimeStop(GamePanel gp){
        super(gp);

        bonus=true;
        type= type_pickupOnly;
        name="Time Stop";
        down1=setup("/object/time_stop",gp.tileSize,gp.tileSize);
    }
}
