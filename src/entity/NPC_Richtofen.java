package entity;

import main.GamePanel;
import object.OBJ_Shovel;
import object.OBJ_Tomahawk;

import java.util.Random;

public class NPC_Richtofen extends Entity{

    private boolean gaveShovel = false;
    public NPC_Richtofen(GamePanel gp){
        super(gp);

        type = type_npc;

        direction = "down";
        speed = 1;

        getImage();
        setDialogue();
    }

    public void getImage(){
        up1 = setup("/npc/richtofen_back_2",gp.tileSize,gp.tileSize);
        up2 = setup("/npc/richtofen_back_3",gp.tileSize,gp.tileSize);
        down1 = setup("/npc/richtofen_face_2",gp.tileSize,gp.tileSize);
        down2 = setup("/npc/richtofen_face_3",gp.tileSize,gp.tileSize);
        left1 = setup("/npc/richtofen_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("/npc/richtofen_left_2",gp.tileSize,gp.tileSize);
        right1 = setup("/npc/richtofen_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("/npc/richtofen_right_2",gp.tileSize,gp.tileSize);
    }

    public void setDialogue(){
        dialogues[0] = "Tiens, tu auras surement besoin de ça...";
    }

    public void setAction(){
        actionLockCounter++;

        if(actionLockCounter==100){
            Random random = new Random();
            int i = random.nextInt(100)+1;
            if(i<=25){
                direction="up";
            }
            if(i>25 && i<=50){
                direction="down";
            }
            if(i>50 && i<=75){
                direction="left";
            }
            if(i>75 && i<=100){
                direction="right";
            }
            actionLockCounter=0;
        }
    }

    public void speak(){
        if (!gaveShovel) {
            gp.player.inventory.add(new OBJ_Shovel(gp));
            gaveShovel = true;
            setDialogue();
        }
        if(gp.player.hasAllTomahawkParts()) {
            gp.player.inventory.add(new OBJ_Tomahawk(gp));
            // Retirez les éléments de l'inventaire du joueur
            gp.player.inventory.remove(gp.player.getIndexOfItem("AxeBlade"));
            gp.player.inventory.remove(gp.player.getIndexOfItem("BlueFlame"));
            gp.player.inventory.remove(gp.player.getIndexOfItem("TomahawkHandle"));
            dialogues[0] = "Ah ! La Tomahawk !";
        }
        super.speak();
    }

}