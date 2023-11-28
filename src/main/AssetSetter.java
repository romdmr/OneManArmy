package main;

import entity.*;
import monster.BasicZombie;
import monster.FatZombie;
import monster.PukeZombie;
import object.*;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        /*
        int i = 0;
        gp.obj[i] = new OBJ_Pistol(gp);
        gp.obj[i].worldX = gp.tileSize*36;
        gp.obj[i].worldY = gp.tileSize*29;
        i++;
        gp.obj[i] = new OBJ_Instakill(gp);
        gp.obj[i].worldX = gp.tileSize*33;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        gp.obj[i] = new OBJ_InfiniteAmmo(gp);
        gp.obj[i].worldX = gp.tileSize*34;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        gp.obj[i] = new OBJ_TimeStop(gp);
        gp.obj[i].worldX = gp.tileSize*35;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        gp.obj[i] = new OBJ_Kaboom(gp);
        gp.obj[i].worldX = gp.tileSize*36;
        gp.obj[i].worldY = gp.tileSize*48;
        i++;
        //AxeBlade
        gp.obj[i] = new OBJ_AxeBlade(gp);
        gp.obj[i].worldX = gp.tileSize*25;
        gp.obj[i].worldY = gp.tileSize*38;
        //BlueFlame
        i++;
        gp.obj[i] = new OBJ_BlueFlame(gp);
        gp.obj[i].worldX = gp.tileSize*57;
        gp.obj[i].worldY = gp.tileSize*53;
        // Placer TomahawkHandle
        i++;
        gp.obj[i] = new OBJ_TomahawkHandle(gp);
        gp.obj[i].worldX = gp.tileSize*36;
        gp.obj[i].worldY = gp.tileSize*28;
        i++;
        gp.obj[i] = new OBJ_DoublePoints(gp);
        gp.obj[i].worldX = gp.tileSize*37;
        gp.obj[i].worldY = gp.tileSize*48;
        */
    }

    public void setNPC(){
        /*RICHTOFEN*/
        gp.npc[0] = new NPC_Richtofen(gp);
        gp.npc[0].worldX = gp.tileSize*27;
        gp.npc[0].worldY = gp.tileSize*43;

        /*StaminUp Machine*/
        gp.npc[1] = new NPC_StaminUp_Machine(gp);
        gp.npc[1].worldX = gp.tileSize*18;
        gp.npc[1].worldY = gp.tileSize*38;

        /*QuickRevive Machine*/
        gp.npc[2] = new NPC_QuickRevive_Machine(gp);
        gp.npc[2].worldX = gp.tileSize*41;
        gp.npc[2].worldY = gp.tileSize*47;

        /*Juggernaut Machine*/
        gp.npc[3] = new NPC_Juggernaut_Machine(gp);
        gp.npc[3].worldX = gp.tileSize*36;
        gp.npc[3].worldY = gp.tileSize*17;

        /*SpeedCola Machine*/
        gp.npc[4] = new NPC_SpeedCola_Machine(gp);
        gp.npc[4].worldX = gp.tileSize*51;
        gp.npc[4].worldY = gp.tileSize*19;

        /*Pack A Punch Machine*/
        gp.npc[5] = new NPC_PackAPunch_Left(gp);
        gp.npc[5].worldX = gp.tileSize*44;
        gp.npc[5].worldY = gp.tileSize*51;
        gp.npc[6] = new NPC_PackAPunch_Right(gp);
        gp.npc[6].worldX = gp.tileSize*45;
        gp.npc[6].worldY = gp.tileSize*51;
    }

    public void setMonster(){
        /*
        gp.monster[0]=new FatZombie(gp,48,42);
        gp.monster[1]=new FatZombie(gp,49,42);
        gp.monster[2]=new FatZombie(gp,50,52);
        gp.monster[0]=new BasicZombie(gp,43,25);
        gp.monster[1]=new FatZombie(gp,24,51);
        gp.monster[2]=new BasicZombie(gp,52,19);
        gp.monster[3]=new FatZombie(gp,27,14);
        gp.monster[4]=new BasicZombie(gp,14,45);
        gp.monster[5]=new FatZombie(gp,25,52);
        gp.monster[6]=new FatZombie(gp,25,51);
        gp.monster[7]=new FatZombie(gp,26,51);
        gp.monster[8]=new PukeZombie(gp,27,51);
        gp.monster[9]=new FatZombie(gp,28,51);
        gp.monster[10]=new BasicZombie(gp,15,45);
        gp.monster[11]=new BasicZombie(gp,16,45);
        gp.monster[12]=new PukeZombie(gp,17,45);
        gp.monster[13]=new BasicZombie(gp,18,45);
        gp.monster[14]=new PukeZombie(gp,19,45);
         */
    }
}
