package entity;

import main.GamePanel;
import main.UtilityTool;
import main.KeyHandler;
import object.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity{
    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    public boolean staminUpAcquired = false;
    public boolean quickReviveAcquired = false;
    public boolean juggernautAcquired = false;
    public boolean speedColaAcquired = false;

    public Player(GamePanel gp, KeyHandler keyH){
        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width=30;
        attackArea.height=30;

        setDefaultValue();
        getPlayerImage();
        getPlayerAttackImage();

        setItems();
    }
    public void setDefaultValue(){
        worldX = gp.tileSize * 34;
        worldY = gp.tileSize * 49;
        speed = 4;
        direction = "down";

        projectile=new OBJ_BasicBullet(gp);

        //PLAYER STATUS
        level = 1;
        maxLife=4;
        life = maxLife;

        maxAmmo=10;
        ammo=maxAmmo;

        strength = 1; // plus il en a, plus il fait des damages.
        dexterity = 1; // plus il en a, moins il reçoit de damages.
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Knife_Normal(gp);
        attack = getAttack(); //
    }
    public void setItems(){
        inventory.add(currentWeapon);
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return attack = strength * currentWeapon.attackValue;
    }
    public void getPlayerImage(){
        up1 = setup("/player/fighter_up1",gp.tileSize,gp.tileSize);
        up2 = setup("/player/fighter_up2",gp.tileSize,gp.tileSize);
        down1 = setup("/player/fighter_down1",gp.tileSize,gp.tileSize);
        down2 = setup("/player/fighter_down2",gp.tileSize,gp.tileSize);
        left1 = setup("/player/fighter_left1",gp.tileSize,gp.tileSize);
        left2 = setup("/player/fighter_left2",gp.tileSize,gp.tileSize);
        right1 = setup("/player/fighter_right1",gp.tileSize,gp.tileSize);
        right2 = setup("/player/fighter_right2",gp.tileSize,gp.tileSize);
    }
    public void getPlayerAttackImage(){
        if(currentWeapon.type == type_knife){
            attack_up1 = setup("/player/attack_up1",gp.tileSize,gp.tileSize*2);
            attack_up2 = setup("/player/attack_up2",gp.tileSize,gp.tileSize*2);
            attack_down1 = setup("/player/attack_down1",gp.tileSize,gp.tileSize*2);
            attack_down2 = setup("/player/attack_down2",gp.tileSize,gp.tileSize*2);
            attack_left1 = setup("/player/attack_left1",gp.tileSize*2,gp.tileSize);
            attack_left2 = setup("/player/attack_left2",gp.tileSize*2,gp.tileSize);
            attack_right1 = setup("/player/attack_right1",gp.tileSize*2,gp.tileSize);
            attack_right2 = setup("/player/attack_right2",gp.tileSize*2,gp.tileSize);
        }
        if(currentWeapon.type == type_pistol){
            attack_up1 = setup("/player/fighter_pistol_up1",gp.tileSize,gp.tileSize*2);
            attack_up2 = setup("/player/fighter_pistol_up2",gp.tileSize,gp.tileSize*2);
            attack_down1 = setup("/player/fighter_pistol_down1",gp.tileSize,gp.tileSize*2);
            attack_down2 = setup("/player/fighter_pistol_down2",gp.tileSize,gp.tileSize*2);
            attack_left1 = setup("/player/fighter_pistol_right1",gp.tileSize*2,gp.tileSize);
            attack_left2 = setup("/player/fighter_pistol_right2",gp.tileSize*2,gp.tileSize);
            attack_right1 = setup("/player/fighter_pistol_left1",gp.tileSize*2,gp.tileSize);
            attack_right2 = setup("/player/fighter_pistol_left2",gp.tileSize*2,gp.tileSize);
        }
    }
    public void update(){

        if(life<maxLife){
            regenCounter++;
            if(regenCounter==480){
                life++;
                regenCounter=0;
            }
        }

        if(attacking){
            attacking();
        }
        else if(keyH.upPressed == true || keyH.downPressed == true ||
                keyH.leftPressed == true || keyH.rightPressed == true ||
                keyH.enterPressed ==true || keyH.cutKeyPressed){
            if(keyH.upPressed == true){
                direction = "up";
            }
            else if (keyH.downPressed == true){
                direction = "down";
            }
            else if (keyH.leftPressed == true){
                direction = "left";
            } else if (keyH.rightPressed == true) {
                direction = "right";
            }

            //check tile collision
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //check object collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //check NPC collsion
            int npcIndex = gp.cChecker.checkEntity(this,gp.npc);
            interactNPC(npcIndex);

            //Check Cut Attack
            if(gp.keyH.cutKeyPressed) {
                attacking=true;
                gp.playSE(6);
            }

            //check MONSTER collison
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            interactMonster(monsterIndex);

            //check event
            gp.eHandler.checkEvent();

            //si collison false, player can't move
            if(collisionOn == false && keyH.enterPressed ==false){
                switch (direction){
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            gp.keyH.enterPressed = false;

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

        if(keyH.reloadKeyPressed && this.ammo<this.maxAmmo){
            reloading=true;
        }

        if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveRessource(this) && !reloading) {
            projectile.set(worldX, worldY, direction, true, this);
            projectile.substractRessource(this);
            gp.projectileList.add(projectile);
            shotAvailableCounter = 0;
            gp.playSE(2);
        }

        if(gp.keyH.ePressed){
            interactWithSpecialTile();
        }

        // Tir avec la Tomahawk
        if(gp.keyH.tomahawkShotKeyPressed && hasTomahawk() && !isTomahawkActive()) {
            launchTomahawk();
        }

        //Invicible time after being hit
        if(this.invicible){
            this.invicibleCounter++;
            if(this.invicibleCounter>60){
                this.invicible=false;
                this.invicibleCounter=0;
            }
        }

        if(shotAvailableCounter<30){
            shotAvailableCounter++;
        }

        //Reset all the atout when life == 0
        if (life <= 0) {
            if (staminUpAcquired) {
                speed -= 2;
                staminUpAcquired = false;
            }
            if (quickReviveAcquired){
                life = maxLife;
                quickReviveAcquired = false;
            } else {
                gp.gameState = gp.gameOverState;
                gp.playMusicNoLoop(16);
            }
            if (juggernautAcquired){
                maxLife -=2;
                life = maxLife;
                juggernautAcquired = false;
            }
            if (speedColaAcquired){
                reloadSpeed = 120;
                speedColaAcquired = false;
            }
        }
    }

    public boolean isTomahawkActive() {
        for (Projectile p : gp.projectileList) {
            if (p instanceof OBJ_Tomahawk && p.alive) {
                return true;
            }
        }
        return false;
    }

    public void launchTomahawk() {
        OBJ_Tomahawk tomahawk = new OBJ_Tomahawk(gp);
        tomahawk.set(worldX, worldY, direction, true, this);
        gp.projectileList.add(tomahawk);
    }

    public boolean hasTomahawk() {
        for(Entity item : inventory) {
            if(item.name.equals("Tomahawk")) {
                return true;
            }
        }
        return false;
    }

    public boolean hasShovel() {
        for(Entity item : inventory) {
            if(item instanceof OBJ_Shovel) { // Remplacez OBJ_Shovel par la classe réelle de la pelle
                return true;
            }
        }
        return false;
    }


    public void interactWithSpecialTile() {
        if (!hasShovel()) {
            return; // Ne rien faire si le joueur n'a pas la pelle
        }

        int playerTileX = worldX/gp.tileSize;
        int playerTileY = worldY/gp.tileSize;

        // Vérifier les tiles adjacentes pour les tiles spéciales
        for(int y = playerTileY - 1; y <= playerTileY + 1; y++) {
            for(int x = playerTileX - 1; x <= playerTileX + 1; x++) {
                if(y >= 0 && y < gp.maxWorldRow && x >= 0 && x < gp.maxWorldCol) {
                    int currentTile = gp.tileM.mapTileNum[x][y];
                    switch (currentTile) {
                        case 73: // BlueFlame
                            OBJ_BlueFlame blueFlame = new OBJ_BlueFlame(gp);
                            inventory.add(blueFlame);
                            //gp.playSE(17);
                            changeTile(73, 76);
                            break;
                        case 74: // TomahawkHandle
                            OBJ_TomahawkHandle tomahawkHandle = new OBJ_TomahawkHandle(gp);
                            inventory.add(tomahawkHandle);
                            //gp.playSE(17);
                            changeTile(74, 77);
                            break;
                        case 75: // AxeBlade
                            OBJ_AxeBlade axeBlade = new OBJ_AxeBlade(gp);
                            inventory.add(axeBlade);
                            //gp.playSE(17);
                            changeTile(75, 78);
                            break;
                    }
                }
            }
        }
    }

    private void changeTile(int oldTile, int newTile) {
        for (int y = 0; y < gp.maxWorldRow; y++) {
            for (int x = 0; x < gp.maxWorldCol; x++) {
                if (gp.tileM.mapTileNum[x][y] == oldTile) {
                    gp.tileM.mapTileNum[x][y] = newTile;
                    return;
                }
            }
        }
    }


    public void attacking(){
        spriteCounter++;
        if(spriteCounter<=5){
            spriteNum=1;
        }
        if(spriteCounter>5 && spriteCounter<=25){
            spriteNum=2;

            //Save the current worldX, worldY, solidArea
            int currentWorldX=worldX;
            int currentWorldY=worldY;
            int solidAreaWidth=solidArea.width;
            int solidAreaHeight=solidArea.height;

            //Adjust player's worldX/Y for the attackArea
            switch(direction){
                case "up":worldY-=attackArea.height; break;
                case "down":worldY+=attackArea.height; break;
                case "left":worldX-=attackArea.width; break;
                case "right":worldX+=attackArea.width; break;
            }

            solidArea.width=attackArea.width;
            solidArea.height=attackArea.height;
            //check monster collison with updated worldX worldY and solidarea
            int monsterIndex = gp.cChecker.checkEntity(this,gp.monster);
            damageMonster(monsterIndex, attack);

            //after collision, restore original data
            worldX=currentWorldX;
            worldY=currentWorldY;
            solidArea.width=solidAreaWidth;
            solidArea.height=solidAreaHeight;
        }
        if(spriteCounter>25){
            spriteNum=1;
            spriteCounter=0;
            attacking=false;
        }

    }
    public void pickUpObject(int i) {
        if (i != 999) {
            //PICK UP ONLY ITEMS
            if(gp.obj[i].type==type_pickupOnly){
                gp.obj[i].use(gp.obj[i].name);
                gp.obj[i]=null;
            }else{
                //INVENTORY ITEMS
                if (inventory.size() < maxInventorySize) {
                    if(!(gp.obj[i] instanceof OBJ_SpawnMark)){
                        inventory.add(gp.obj[i]);
                    }
                    if(Objects.equals(gp.obj[i].name, "StaminUp")){
                        speed += 2;
                    }
                    else if(Objects.equals(gp.obj[i].name, "QuickRevive")){
                        life = maxLife;
                    }
                }
                if(!(gp.obj[i] instanceof OBJ_SpawnMark)){
                    gp.obj[i] = null;
                }
            }
        }
    }

    public boolean hasAllTomahawkParts() {
        return getIndexOfItem("AxeBlade") != -1 &&
                getIndexOfItem("BlueFlame") != -1 &&
                getIndexOfItem("TomahawkHandle") != -1;
    }

    public int getIndexOfItem(String itemName) {
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                return i;
            }
        }
        return -1;
    }


    public void interactNPC(int i){
            if(i != 999){
                if(gp.keyH.enterPressed) {
                    gp.gameState = gp.dialogueState;
                    gp.npc[i].speak();
                }
            }
    }
    public void interactMonster(int i){
        if(i!=999){
            if(invicible==false && gp.monster[i].dying==false){

                gp.playSE(9);
                int damage = gp.monster[i].attack-defense;
                if(damage<0){
                    damage=0;
                }

                life-=damage;
                invicible=true;
            }
        }
    }


    public void damageMonster(int i, int attack){
        if(i!= 999){
            if(!gp.monster[i].invicible) {
                gp.playSE(7);

                int damage = attack- gp.monster[i].defense;
                if(damage<0){
                    damage=0;
                }

                gp.monster[i].life -= damage;
                gp.monster[i].invicible=true;

                if (gp.monster[i].life <= 0) {
                    gp.monster [i].dying= true;
                    if(gp.monster [i].name=="Fat Zombie"){
                        if(doublePointsIsOn){
                            coin += 250;
                        }else{
                            coin += 125;
                        }
                        gp.playSE(4);
                    }
                    if(gp.monster [i].name=="Basic Zombie"){
                        if(doublePointsIsOn){
                            coin += 150;
                        }else{
                            coin += 75;
                        }
                        gp.playSE(5);
                    }
                    if(gp.monster [i].name=="Puke Zombie"){
                        if(doublePointsIsOn){
                            coin += 350;
                        }else{
                            coin += 175;
                        }
                        gp.playSE(8);
                    }
                }
            }
        }
    }
    public void selectItem(){
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
        if(itemIndex < inventory.size()){
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_knife || selectedItem.type == type_pistol){
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if(selectedItem.type == type_consumable){
                //later
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX=screenX;
        int tempScreenY=screenY;

        switch(direction){
            case "up":
                if(!attacking){
                    if(spriteNum == 1) {image = up1;}
                    if (spriteNum == 2) {image = up2;}
                }else{
                    tempScreenY=screenY-gp.tileSize;
                    if(spriteNum == 1) {image = attack_up1;}
                    if (spriteNum == 2) {image = attack_up2;}
                }
                break;
            case "down":
                if(!attacking){
                    if(spriteNum == 1) {image = down1;}
                    if (spriteNum == 2) {image = down2;}
                }else{
                    if(spriteNum == 1) {image = attack_down1;}
                    if (spriteNum == 2) {image = attack_down2;}
                }
                break;
            case "left":
                if(!attacking){
                    if(spriteNum == 1) {image = left1;}
                    if (spriteNum == 2) {image = left2;}
                }else{
                    tempScreenX=screenX-gp.tileSize;
                    if(spriteNum == 1) {image = attack_left1;}
                    if (spriteNum == 2) {image = attack_left2;}
                }
                break;
            case "right":
                if(!attacking){
                    if(spriteNum == 1) {image = right1;}
                    if (spriteNum == 2) {image = right2;}
                }else{
                    if(spriteNum == 1) {image = attack_right1;}
                    if (spriteNum == 2) {image = attack_right2;}
                }
                break;
        }

        //reloading bar
        if(reloading){
            reloadCounter++;
            if(reloadCounter==1){
                if(speedColaAcquired){
                    gp.playSE(17);
                }else{
                    gp.playSE(10);
                }
            }

            g2.setColor(new Color(79,67,48));
            g2.fillRect(screenX-1,screenY-16,gp.tileSize+2,12);

            // Draw the filled portion of the health bar based on the current health
            int filledWidth = (int) ((double) reloadCounter / reloadSpeed * gp.tileSize+2);
            g2.setColor(Color.ORANGE);
            g2.fillRect(screenX, screenY-15, filledWidth, 10);
            g2.setColor((Color.WHITE));

            g2.setColor(Color.WHITE);
            g2.setFont(g2.getFont().deriveFont(10F));
            g2.drawString("RELOADING !", screenX-8, screenY-16);

            //fin de rechargement
            if(reloadCounter == reloadSpeed){
                reloadCounter=0;
                reloading=false;
                ammo=maxAmmo;
            }
        }

        //Visual effect invicible
        if(invicible){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4F));
        }
        g2.drawImage(image, tempScreenX, tempScreenY,null);

        //Reset invicible effect
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
    }

}
