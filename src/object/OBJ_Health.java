package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Health extends Entity {

    public OBJ_Health(GamePanel gp){
        super(gp);

        name="Health";
        healthEmpty = setup("/object/HealthEmpty",gp.tileSize,gp.tileSize);
        healthFull = setup("/object/HealthFull",gp.tileSize,gp.tileSize);
        addHealth = setup("/object/addHealth",gp.tileSize,gp.tileSize);
        screenDamage = setup("/object/BloodOverlay",gp.tileSize*20,gp.tileSize*12);
    }
}
