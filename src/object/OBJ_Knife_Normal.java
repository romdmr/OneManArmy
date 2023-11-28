package object;

import entity.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public class OBJ_Knife_Normal extends Entity{
    public OBJ_Knife_Normal(GamePanel gp){
        super(gp);

        type = type_knife;

        bonus=false;
        name = "Knife";
        down1 = setup("/object/knife",gp.tileSize,gp.tileSize);
        attackArea.width = 36;
        attackArea.height = 36;
        attackValue = 1;
        description = name + " - Cut everything !";
    }

}
