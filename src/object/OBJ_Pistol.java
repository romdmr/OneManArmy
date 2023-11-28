package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pistol extends Entity {
    public OBJ_Pistol(GamePanel gp){
        super(gp);

        type = type_pistol;

        bonus=false;
        name = "Pistol";
        down1 = setup("/object/pistol", gp.tileSize, gp.tileSize);

        description = name + " - Glock 17 !";

        attackArea.width = 36;
        attackArea.height = 36;
        attackValue = 1;

    }
}
