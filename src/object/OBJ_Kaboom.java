package object;

import entity.Entity;
import entity.Player;
import main.GamePanel;

public class OBJ_Kaboom extends Entity {

    public OBJ_Kaboom(GamePanel gp){
        super(gp);

        bonus=true;
        type= type_pickupOnly;
        name="Kaboom";
        down1=setup("/object/Kaboom",gp.tileSize,gp.tileSize);
        down2=setup("/object/NukeEffect",gp.tileSize,gp.tileSize);
    }
}
