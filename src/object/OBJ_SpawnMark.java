package object;

import main.GamePanel;
import entity.Entity;

public class OBJ_SpawnMark extends Entity{

    GamePanel gp;
    public OBJ_SpawnMark(GamePanel gp){
        super(gp);

        name = "AxeBlade";
        down1 = setup("/zombie/spawnMark",gp.tileSize,gp.tileSize);
    }
}