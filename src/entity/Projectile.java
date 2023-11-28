package entity;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Projectile extends Entity{

    Entity user;

    public Projectile(GamePanel gp){
        super(gp);
    }

    public void set(int worldX,int worldY, String direction, boolean alive, Entity user){
        this.worldX=worldX;
        this.worldY=worldY;
        this.direction=direction;
        this.alive=alive;
        this.user=user;
        this.life=this.maxLife;
    }

    public void update(){
        if(user==gp.player){
            int monsterIndex=gp.cChecker.checkEntity(this,gp.monster);
            if(monsterIndex != 999){
                gp.player.damageMonster(monsterIndex, attack);
                alive = false;
            }
        }
        if(user!=gp.player){
            boolean contactPlayer=gp.cChecker.checkPlayer(this);
            if(!gp.player.invicible && contactPlayer){
                damagePlayer(attack);
                alive=false;
            }
        }

        switch(direction){
            case"up":worldY-=speed;break;
            case"down":worldY+=speed;break;
            case"left":worldX-=speed;break;
            case"right":worldX+=speed;break;
        }

        life--;
        if(life<=0){
            alive=false;
        }

        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1){
                spriteNum = 2;
            }
            else if (spriteNum == 2) {
                spriteNum = 3;
            }else if (spriteNum == 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    public boolean haveRessource(Entity user){
        boolean haveRessource=false;
        return haveRessource;
    }

    public void substractRessource(Entity user){
    }

    public void draw(Graphics2D g2){

        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch(direction){
                case "up":
                    if(spriteNum == 1 && !isPaped) {
                        image = up1;
                    } else if(spriteNum == 1 && isPaped) {
                        image = paped_up1;
                    }
                    if (spriteNum == 2 && !isPaped) {
                        image = up2;
                    } else if(spriteNum == 2 && isPaped) {
                        image = paped_up2;
                    }
                    if (spriteNum == 3 && !isPaped) {
                        image = up3;
                    } else if(spriteNum == 2 && isPaped) {
                        image = paped_up2;
                    }
                    break;
                case "down":
                    if(spriteNum == 1 && !isPaped) {
                        image = down1;
                    } else if(spriteNum == 1 && isPaped) {
                        image = paped_down1;
                    }
                    if (spriteNum == 2 && !isPaped) {
                        image = down2;
                    } else if(spriteNum == 2 && isPaped) {
                        image = paped_down2;
                    }
                    if (spriteNum == 3 && !isPaped) {
                        image = down3;
                    } else if(spriteNum == 3 && isPaped) {
                        image = paped_down3;
                    }
                    break;
                case "left":
                    if(spriteNum == 1 && !isPaped) {
                        image = left1;
                    } else if(spriteNum == 1 && isPaped) {
                        image = paped_left1;
                    }
                    if (spriteNum == 2 && !isPaped) {
                        image = left2;
                    } else if(spriteNum == 2 && isPaped) {
                        image = paped_left2;
                    }
                    if (spriteNum == 3 && !isPaped) {
                        image = left3;
                    } else if(spriteNum == 3 && isPaped) {
                        image = paped_left3;
                    }
                    break;
                case "right":
                    if(spriteNum == 1 && !isPaped) {
                        image = right1;
                    } else if(spriteNum == 1 && isPaped) {
                        image = paped_right1;
                    }
                    if (spriteNum == 2 && !isPaped) {
                        image = right2;
                    } else if(spriteNum == 2 && isPaped) {
                        image = paped_right2;
                    }
                    if (spriteNum == 3 && !isPaped) {
                        image = right3;
                    } else if(spriteNum == 3 && isPaped) {
                        image = paped_right3;
                    }
                    break;
            }
            g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
