package entity;

import ai.Node;
import main.GamePanel;
import main.UtilityTool;
import monster.Zombie;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    public GamePanel gp;
    public BufferedImage upInit, up1, up2, up3, downInit, down1, down2, down3, leftInit, left1, left2, left3, rightInit, right1, right2, right3, spawnMark, healthFull, healthEmpty, addHealth, screenDamage, paped_up1, paped_up2, paped_up3, paped_left1, paped_left2, paped_left3, paped_right1, paped_right2, paped_right3, paped_down1, paped_down2, paped_down3;;
    public BufferedImage attack_down1,attack_down2,attack_left1,attack_left2,attack_up1,attack_up2,attack_right1,attack_right2;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea= new Rectangle(0,0,0,0);
    String dialogues[] = new String[20];
    public BufferedImage image;
    public BufferedImage image1,image2;
    public boolean collision = false;
    public int solidAreaDefaultX,solidAreaDefaultY;

    //CHARACTER ATTRIBUTES
    public int maxAmmo;
    public int ammo;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public boolean isPaped = false;

    //ITEM ATTRIBUTES
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;
    public int attackValue;
    public String description = "";
    public int price;

    public Projectile projectile;

    public boolean bonus = false;
    public int nbInstakill=0;
    public int nbInfiniteAmmo=0;
    public int nbTimeStop=0;
    public int nbDoublePoints=0;

    //STATE
    public int worldX, worldY;
    public String direction="down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invicible=false;
    boolean attacking =false;
    public boolean alive = true;
    public boolean dying =false;
    public boolean reloading=false;
    public boolean onPath = false;
    public boolean instakillIsOn=false;
    public boolean infiniteAmmoIsOn=false;
    public boolean timeStopIsOn=false;
    public boolean kaboomIsOn=false;
    public boolean doublePointsIsOn=false;


    //COUNTER
    public int spriteCounter = 0;
    public int actionLockCounter=0;
    public int invicibleCounter=0;
    int dyingCounter=0;
    public int shotAvailableCounter=0;
    public int reloadCounter=0;
    public int reloadSpeed=120;
    public int objectShowCounter=0;
    public int regenCounter=0;


    //CHARACTER ATTRIBUTE
    public String name;
    public int speed;
    public int maxLife;
    public int life;

    //TYPE
    public int type; // 0=player, 1=NPC 2=Monster
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public final int type_knife = 3;
    public final int type_pistol = 4;
    public final int type_consumable = 5;
    public final int type_pickupOnly=6;


    public Entity(GamePanel gp){
        this.gp = gp;
    }
    public void setAction(){}

    public void checkDrop(){}

    public void dropItem(Entity droppedItem){
        for(int i=0;i<gp.obj.length;i++){
            if(gp.obj[i] == null){
                gp.obj[i]=droppedItem;
                gp.obj[i].worldX=worldX;
                gp.obj[i].worldY=worldY;
                break;
            }
        }
    }

    public void damageReaction(){}
    public void speak(){
        if(dialogues[dialogueIndex] == null){
            dialogueIndex = 0;
        }

        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch(gp.player.direction){
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void checkCollision(){
        collisionOn = false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkObject(this, false);
        gp.cChecker.checkEntity(this,gp.npc);
        gp.cChecker.checkEntity(this,gp.monster);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);

        if(this.type== type_monster && contactPlayer==true){
            damagePlayer(attack);
        }
    }
    public void update(){

        setAction();
        checkCollision();


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
    }
    public void damagePlayer(int attack){
        if(!gp.player.invicible){
            gp.playSE(9);
            int damage = attack-gp.player.defense;
            if(damage<0){
                damage=0;
            }
            gp.player.life-=damage;
            gp.player.invicible=true;
        }
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
                    if(spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                    break;
                case "down":
                    if(spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                    break;
                case "left":
                    if(spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                    break;
                case "right":
                    if(spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                    break;
            }

            //Monster Hp Bar
            if(type==2){

                double oneScale=(double)gp.tileSize/maxLife;
                double hpBarValue=oneScale*life;

                if(life!=maxLife){
                    g2.setColor(new Color(79,67,48));
                    g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

                    g2.setColor(new Color(255,0,30));
                    g2.fillRect(screenX,screenY-15,(int)hpBarValue,10);
                }
            }

            if(invicible){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4F));
            }
            if(dying){
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
        }

    }
    public void dyingAnimation(Graphics2D g2){

        dyingCounter++;

        int i=5;

        if(dyingCounter<=i){changeAlpha(g2,0.4F);}
        if(dyingCounter>i && dyingCounter<=i*2){changeAlpha(g2,1F);}
        if(dyingCounter>i*2 && dyingCounter<=i*3){changeAlpha(g2,0.4F);}
        if(dyingCounter>i*3 && dyingCounter<=i*4){changeAlpha(g2,1F);}
        if(dyingCounter>i*4 && dyingCounter<=i*5){changeAlpha(g2,0.4F);}
        if(dyingCounter>i*5 && dyingCounter<=i*6){changeAlpha(g2,1F);}
        if(dyingCounter>i*6 && dyingCounter<=i*7){changeAlpha(g2,0.4F);}
        if(dyingCounter>i*7 && dyingCounter<=i*8){changeAlpha(g2,1F);}
        if(dyingCounter>i*8){
            alive=false;
        }
    }
    public void changeAlpha(Graphics2D g2,float alphaValue){
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alphaValue));
    }
    public BufferedImage setup(String imagePath, int width, int height){

        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
            image = uTool.scaleImage(image, width, height);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    /*
    public void searchPath(int goalCol, int goalRow){
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol ,startRow, goalCol, goalRow);

        if(gp.pFinder.search() == true){
            int nextX = gp.pFinder.pathList.get(0).col * gp.tileSize;
            int nextY = gp.pFinder.pathList.get(0).row * gp.tileSize;

            int entityLeftX = worldX + solidArea.x;
            int entityRightX = worldX + solidArea.x + solidArea.width;
            int entityTopY = worldY + solidArea.y;
            int entityBottomY = worldY + solidArea.y + solidArea.height;

            if(entityTopY > nextY && entityLeftX >= nextX && entityRightX < nextX + gp.tileSize){
                direction = "up";
            }
            else if(entityTopY < nextY && entityLeftX >= nextX && entityRightX < nextX + gp.tileSize){
                direction = "down";
            }
            else if(entityTopY >= nextY && entityBottomY < nextY + gp.tileSize){
                if(entityLeftX > nextX){
                    direction = "left";
                }
                if(entityLeftX < nextX){
                    direction = "right";
                }
            }
            else if(entityTopY > nextY && entityLeftX > nextX){
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(entityTopY > nextY && entityLeftX < nextX){
                direction = "up";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }
            else if(entityTopY < nextY && entityLeftX > nextX){
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "left";
                }
            }
            else if(entityTopY < nextY && entityLeftX < nextX){
                direction = "down";
                checkCollision();
                if(collisionOn == true){
                    direction = "right";
                }
            }

            int nextCol = gp.pFinder.pathList.get(0).col;
            int nextRow = gp.pFinder.pathList.get(0).row;

            if(nextCol == goalCol && nextRow == goalRow){
                onPath = false;
            }
        }
    }
    */

    public void searchPath(int goalCol, int goalRow) {
        int startCol = (worldX + solidArea.x) / gp.tileSize;
        int startRow = (worldY + solidArea.y) / gp.tileSize;

        gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

        if (gp.pFinder.search()) {
            Node nextNode = gp.pFinder.pathList.get(0);
            int nextX = nextNode.col * gp.tileSize;
            int nextY = nextNode.row * gp.tileSize;

            setDirectionTowards(nextX, nextY);

            if (nextNode.col == goalCol && nextNode.row == goalRow) {
                onPath = false;
            }
        }
    }

    private void setDirectionTowards(int nextX, int nextY) {
        int horizontalDistance = nextX - worldX;
        int verticalDistance = nextY - worldY;
        int totalDistance = Math.abs(horizontalDistance) + Math.abs(verticalDistance);

        // Détermine la direction basée sur la plus grande distance
        if (Math.abs(horizontalDistance) > Math.abs(verticalDistance)) {
            direction = (horizontalDistance > 0) ? "right" : "left";
        } else {
            direction = (verticalDistance > 0) ? "down" : "up";
        }

        // Ajuste la vitesse en fonction de la distance totale
        adjustSpeedBasedOnDistance(totalDistance);
    }

    private void adjustSpeedBasedOnDistance(int distance) {
        if(!gp.player.timeStopIsOn){
            if (distance < 3 * gp.tileSize) {
                speed = 1; // Vitesse réduite
            } else {
                speed = 2; // Vitesse normale
            }
        }
    }


    protected void use(String name) {
        switch(name){
            case "Instakill":
                gp.playSE(11);
                gp.player.instakillIsOn=true;
                gp.player.nbInstakill++;
                break;
            case "Infinite Ammo":
                gp.playSE(12);
                gp.player.infiniteAmmoIsOn=true;
                gp.player.ammo=gp.player.maxAmmo;
                gp.player.nbInfiniteAmmo++;
                break;
            case "Time Stop":
                gp.playSE(13);
                gp.player.timeStopIsOn=true;
                gp.player.nbTimeStop++;
                break;
            case "Kaboom":
                gp.playSE(14);
                gp.player.kaboomIsOn=true;
                break;
            case "Double Points":
                gp.playSE(18);
                gp.player.doublePointsIsOn=true;
                gp.player.nbDoublePoints++;
                break;
        }
    }
}