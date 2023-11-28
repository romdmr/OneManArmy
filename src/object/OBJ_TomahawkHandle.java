package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_TomahawkHandle extends Entity {
    GamePanel gp;
    public OBJ_TomahawkHandle(GamePanel gp){
        super(gp);

        name = "TomahawkHandle";
        down1 = setup("/object/tomahawk_handle",gp.tileSize,gp.tileSize);
    }
}
