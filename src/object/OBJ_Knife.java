package object;

import main.GamePanel;

import entity.Entity;

public class OBJ_Knife extends Entity{

    public OBJ_Knife(GamePanel gp){

        super(gp);

        type = type_knife;

        bonus=false;
        name = "Knife";
        down1=setup("/object/knife",gp.tileSize,gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
    }
}