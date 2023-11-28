package monster;

import entity.Entity;
import main.GamePanel;
import object.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Zombie extends Entity {

    public final int screenX;
    public final int screenY;

    public int stockedLife;

    public int actionLockCounter=0;

    public Zombie(GamePanel gp, String type, int x, int y){
        super(gp);
        this.type = type_monster;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);


        setDefaultValue(x, y);
        getZombieImage(type);
    }

    public void setDefaultValue( int x, int y){
        worldX = gp.tileSize * x;
        worldY = gp.tileSize * y;
    }

    public void getZombieImage(String type){
            upInit = setup("/zombie/"+ type +"_upInit",gp.tileSize,gp.tileSize);
            up1 = setup("/zombie/"+ type +"_up1",gp.tileSize,gp.tileSize);
            up2 = setup("/zombie/"+ type +"_up2",gp.tileSize,gp.tileSize);
            downInit = setup("/zombie/"+ type +"_downInit",gp.tileSize,gp.tileSize);
            down1 = setup("/zombie/"+ type +"_down1",gp.tileSize,gp.tileSize);
            down2 = setup("/zombie/"+ type +"_down2",gp.tileSize,gp.tileSize);
            leftInit = setup("/zombie/"+ type +"_leftInit",gp.tileSize,gp.tileSize);
            left1 = setup("/zombie/"+ type +"_left1",gp.tileSize,gp.tileSize);
            left2 = setup("/zombie/"+ type +"_left2",gp.tileSize,gp.tileSize);
            rightInit = setup("/zombie/"+ type +"_rightInit",gp.tileSize,gp.tileSize);
            right1 = setup("/zombie/"+ type +"_right1",gp.tileSize,gp.tileSize);
            right2 = setup("/zombie/"+ type +"_right2",gp.tileSize,gp.tileSize);
            spawnMark = setup("/zombie/spawnMark",gp.tileSize,gp.tileSize);
    }

    public void setAction() {
        if(onPath == true){
            int goalCol = (gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow = (gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;

            searchPath(goalCol, goalRow);

            int i = new Random().nextInt(300) + 1;
            if(i>299 && projectile!=null && projectile.alive == false && shotAvailableCounter==30){
                projectile.set(worldX,worldY,direction,true,this);
                gp.projectileList.add(projectile);
                shotAvailableCounter=0;
            }
        }
        else {
            actionLockCounter++;

            if (actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;

                if (i <= 25) {
                    direction = "up";
                }
                if (i > 25 && i <= 50) {
                    direction = "down";
                }
                if (i > 50 && i <= 75) {
                    direction = "left";
                }
                if (i > 75 && i <= 100) {
                    direction = "right";
                }
                actionLockCounter = 0;
            }
        }
    }

    public void update(){

        super.update();
        setAction();

        int xDistance = Math.abs(worldX - gp.player.worldX);
        int yDistance = Math.abs(worldY - gp.player.worldY);

        int tileDistance = (xDistance + yDistance) / gp.tileSize;

        if(onPath == false && tileDistance < 999 ){
            onPath = true;
        }

        collisionOn=false;
        gp.cChecker.checkTile(this);
        boolean contactPlayer=gp.cChecker.checkPlayer(this);

        if(this.type==2 && contactPlayer==true){
            damagePlayer(attack);
        }

        if(collisionOn == false){
            switch (direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }

        if(this.invicible){

            this.invicibleCounter++;
            if(this.invicibleCounter>30){
                this.invicible=false;
                this.invicibleCounter=0;
            }
        }

        if(shotAvailableCounter<30){
            shotAvailableCounter++;
        }
    }

    public void checkDrop(){
        int i =new Random().nextInt(100)+1;

        //Set the monster srop
        if(i<25){
            int x=new Random().nextInt(5)+1;
            if(x==1){
                dropItem(new OBJ_Instakill(gp));
            }
            if(x==2){
                dropItem(new OBJ_InfiniteAmmo(gp));
            }
            if(x==3){
                dropItem(new OBJ_TimeStop(gp));
            }
            if(x==4){
                dropItem(new OBJ_Kaboom(gp));
            }
            if(x==5){
                dropItem(new OBJ_DoublePoints(gp));
            }
        }
    }
}
