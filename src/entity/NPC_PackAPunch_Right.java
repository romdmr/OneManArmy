package entity;

import main.GamePanel;

public class NPC_PackAPunch_Right extends Entity{
    public NPC_PackAPunch_Right(GamePanel gp) {
        super(gp);

        type = type_npc;
        direction = "down";

        price = 5000;

        getImage();
        setDialogue();
    }
    public void getImage(){
        up1 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/pack_a_punch_right",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0] = "Unleash your power !";
    }

    public void speak(){
        super.speak();
        gp.gameState = gp.papState;
        gp.ui.machine = this;
    }
}
