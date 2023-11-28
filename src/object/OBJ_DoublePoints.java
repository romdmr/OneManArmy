package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_DoublePoints extends Entity {

    public OBJ_DoublePoints(GamePanel gp){
        super(gp);

        bonus=true;
        type= type_pickupOnly;
        name="Double Points";
        down1=setup("/object/double_points",gp.tileSize,gp.tileSize);
    }
}
