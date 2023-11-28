package main;

import monster.BasicZombie;
import monster.Zombie;
import object.*;
import object.OBJ_AmmoIcon;
import object.OBJ_Coin;
import object.OBJ_Instakill;
import object.OBJ_StaminUp;
import entity.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage ammo_full, ammo_empty, coin, instakill, infiniteAmmo, timeStop, kaboom, nukeEffect, doublePoints,healthEmpty,healthFull,addHealth,screenDamage,spawnMark;;
    public boolean messageOn = false;
    public String message = "";

    int messageCounter = 0;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int machineSlotCol = 0;
    public int machineSlotRow = 0;
    public int reloadAnimationCounter=0;
    public int subState;
    public Entity machine;

    public int slotCol = 0;
    public int slotRow = 0;
    public int nbInstakill=0;
    public int nbInfiniteAmmo=0;
    public int nbTimeStop=0;
    public int nbDoublePoints=0;

    public int instakillCounter=0;
    public int infiniteAmmoCounter=0;
    public int timeStopCounter=0;
    public int kaboomCounter=0;
    public int doublePointsCounter=0;
    public int playerProjectileDamageInstakill;

    public UI(GamePanel gp){
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        Entity ammo =new OBJ_AmmoIcon(gp);
        ammo_full = ammo.image2;
        ammo_empty = ammo.image1;
        Entity moneyCoin = new OBJ_Coin(gp);
        coin = moneyCoin.down1;
        ammo_full= ammo.image2;
        ammo_empty=ammo.image1;
        Entity ik=new OBJ_Instakill(gp);
        instakill=ik.down1;
        Entity ma=new OBJ_InfiniteAmmo(gp);
        infiniteAmmo=ma.down1;
        Entity ts=new OBJ_TimeStop(gp);
        timeStop=ts.down1;
        Entity kb=new OBJ_Kaboom(gp);
        kaboom=kb.down1;
        nukeEffect=kb.down2;
        Entity dp=new OBJ_DoublePoints(gp);
        doublePoints=dp.down1;
        Entity h=new OBJ_Health(gp);
        healthEmpty=h.healthEmpty;
        healthFull=h.healthFull;
        addHealth=h.addHealth;
        screenDamage=h.screenDamage;
        Entity z=new BasicZombie(gp,35,35);
        spawnMark=z.spawnMark;
    }
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        //TITLE STATE
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }
        //PREGAME STATE
        if (gp.gameState == gp.pregameState){
            drawPregameScreen();
        }
        //PLAY STATE
        if(gp.gameState == gp.playState){
            drawPlayerInfo(g2);
        }

        //GAME OVER
        if (gp.gameState == gp.gameOverState){
            drawGameOver();
        }

        //PAUSE STATE
        if(gp.gameState == gp.pauseState){
            drawPauseScreen();
        }

        //OPTIONS STATE
        if (gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }

        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState){
            drawDialogueScreen();
        }

        //CHARACTER STATE
        if(gp.gameState == gp.characterState){
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        //TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        //PACK-A-PUNCH STATE
        if(gp.gameState == gp.papState){
            drawPAPScreen();
        }
    }

    private void drawPAPScreen() {
        switch(subState){
            case 0 : pap_select(); break;
            case 1 : pap_buy(); break;
        }

        gp.keyH.enterPressed = false;
    }

    public void pap_select() {
        drawDialogueScreen();

        //DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 4;
        int height = (int)(gp.tileSize * 2.5);
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Upgrade", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                if (gp.player.coin >= machine.price && !gp.player.projectile.isPaped) {
                    gp.player.projectile.attack = gp.player.projectile.attack * 2;
                    gp.player.projectile.isPaped = true;
                    gp.player.coin -= machine.price;
                    gp.gameState = gp.playState;
                } else if(gp.player.coin < machine.price) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more money to upgrade your weapon!";
                    drawDialogueScreen();
                } else if (gp.player.projectile.isPaped){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "Your weapon is already upgraded!";
                    drawDialogueScreen();
                }
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.playState;
            }
        }
    }

    public void pap_buy(){
        drawInventory(machine, true);

        //ESC Window
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //PLAYER MONEY WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Money: " + gp.player.coin, x+24, y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(machineSlotCol, machineSlotRow);
        if(itemIndex < machine.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = machine.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*8-20);
            g2.drawString(text, x, y+34);

            //BUY AN ITEM
            if(gp.keyH.enterPressed == true){
                //Check player's money
                if(machine.inventory.get(itemIndex).price > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more money to buy it !";
                    drawDialogueScreen();
                }

                if (gp.player.coin >= machine.inventory.get(itemIndex).price) {

                    gp.player.coin -= machine.inventory.get(itemIndex).price;

                    gp.player.currentWeapon.attack = gp.player.currentWeapon.attack*2;
                } else {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more money to buy " + machine.inventory.get(itemIndex).name + "!";
                    drawDialogueScreen();
                }
            }
        }
    }

    private void drawGameOver() {
        Color overlayColor = new Color(171, 26, 26, 200); // 0-255 pour les composantes RVBA
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "GAME OVER";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

        //GAME OVER MENU
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        text = "STATS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }

    private void drawTitleScreen() {
        Image bgImage = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/background/backgroundMenu.jpg"));

        g2.drawImage(bgImage, 0, 0, gp.getWidth(), gp.getHeight(), null);

        // TITLE NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)); // Adjusted font size
        String text = "One Man Army";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        text = "PLAY";
        x = getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "STATS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "CREDITS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 3){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    private void drawPregameScreen() {
        Image bgScreen = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pregame/bgScreen.png"));

        g2.drawImage(bgScreen, 0, 0, gp.getWidth(), gp.getHeight(), null);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        String text = "How to play the game ?";
        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        //CONTROLS IMGS
        Image movements = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pregame/movements.png"));
        Image attack = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pregame/attack.png"));
        Image shoot = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pregame/shoot.png"));
        Image interact = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pregame/interact.png"));
        Image enter = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pregame/enter.png"));

        //movements
        x = gp.screenWidth/5+gp.tileSize;
        y += gp.tileSize*2;
        g2.drawImage(movements, x, y, movements.getWidth(null), movements.getHeight(null), null);

        //shoot
        x = gp.screenWidth/7-gp.tileSize*2;
        y += gp.tileSize;
        g2.drawImage(shoot, x, y, shoot.getWidth(null), shoot.getHeight(null), null);

        //interact
        x = gp.screenWidth/3+gp.tileSize*5;
        g2.drawImage(interact, x, y, interact.getWidth(null), interact.getHeight(null), null);

        //attack
        x = gp.screenWidth/2+gp.tileSize*6;
        g2.drawImage(attack, x, y, attack.getWidth(null), attack.getHeight(null), null);

        //enter
        x = gp.screenWidth/4;
        y += gp.tileSize*4;
        g2.drawImage(enter, x, y, enter.getWidth(null), enter.getHeight(null), null);


    }
    public void drawPauseScreen() {
        // Appliquer un filtre noir partiellement opaque
        Color overlayColor = new Color(0, 0, 0, 200); // 0-255 pour les composantes RVBA
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

        //PAUSE MENU
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));

        text = "RESUME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "OPTIONS";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        text = "QUIT GAME";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 2){
            g2.drawString(">", x-gp.tileSize, y);
        }
    }
    private void drawOptionsScreen() {
        // Appliquer un filtre noir partiellement opaque
        Color overlayColor = new Color(0, 0, 0, 200); // 0-255 pour les composantes RVBA
        g2.setColor(overlayColor);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 65F));
        String text = "OPTIONS";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/4;
        g2.drawString(text, x, y);

        //MUSIC
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        text = "MUSIC";
        x = gp.screenWidth/6;
        y = gp.screenHeight/3+gp.tileSize;
        g2.drawString(text, x, y);
        if(commandNum == 0){
            g2.drawString(">", x-gp.tileSize, y);
        }

        //MUSIC VOLUME
        x = gp.screenWidth/3;
        y -= gp.tileSize/2;
        g2.drawRect(x, y, 400, 24);
        int volumeWidth = 80 * gp.music.volumeScale;
        g2.fillRect(x, y, volumeWidth, 24);

        //SFX
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
        text = "SFX";
        x = gp.screenWidth/6;
        y = gp.screenHeight/2;
        g2.drawString(text, x, y);
        if(commandNum == 1){
            g2.drawString(">", x-gp.tileSize, y);
        }

        //SFX VOLUME
        x = gp.screenWidth/3;
        y -= gp.tileSize/2;
        g2.drawRect(x, y, 400, 24);
        volumeWidth = 80 * gp.se.volumeScale;
        g2.fillRect(x, y, volumeWidth, 24);

    }
    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*3;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*6);
        int height= gp.tileSize*3;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 26F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }
    public void drawCharacterScreen(){
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        //NAMES
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Money", textX, textY);
        textY += lineHeight + 30;
        g2.drawString("Weapon", textX, textY);

        //VALUES
        int tailX = (frameX + frameWidth) - 30;
        //Reset Text
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY, null);
        //textY += gp.tileSize;

    }
    public void drawInventory(Entity entity, boolean cursor){

        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if(entity == gp.player){
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }
        else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = machineSlotCol;
            slotRow = machineSlotRow;
        }


        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        //DRAW PLAYER'S ITEM
        for(int i = 0; i < entity.inventory.size(); i++){

            //EQUIP CURSOR
            if(entity.inventory.get(i) == entity.currentWeapon){
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if(i == 4 || i == 9 || i == 14){
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //CURSOR
        if(cursor == true){
            int cursorX = slotXstart + (slotSize * playerSlotCol);
            int cursorY = slotYstart + (slotSize * playerSlotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //DRAW CURSOR (carré)
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);


            //DESCRIPTION FRAME
            int dframeX = frameX;
            int dframeY = frameY + frameHeight;
            int dframeWidth = frameWidth;
            int dframeHeight = gp.tileSize*3;

            //DRAW DESCRIPTION TEXT
            int textX = dframeX + 20 ;
            int textY = dframeY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(20F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if(itemIndex < entity.inventory.size()){

                drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight); // description window only appear in item

                for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }

    }

    public void drawTradeScreen(){
        switch(subState){
            case 0 : trade_select(); break;
            case 1 : trade_buy(); break;
        }

        gp.keyH.enterPressed = false;
    }

    public void trade_select(){
        drawDialogueScreen();

        //DRAW WINDOW
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int)(gp.tileSize * 2.5);
        drawSubWindow(x, y, width, height);

        x += gp.tileSize;
        y += gp.tileSize;
        g2.drawString("Buy", x, y);
        if(commandNum == 0){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                subState = 1;
            }
        }
        y += gp.tileSize;
        g2.drawString("Leave", x, y);
        if(commandNum == 1){
            g2.drawString(">", x-24, y);
            if(gp.keyH.enterPressed == true){
                commandNum = 0;
                gp.gameState = gp.playState;
            }
        }
    }

    public void trade_buy(){
        //drawInventory(gp.player, false);
        drawInventory(machine, true);

        //ESC Window
        int x = gp.tileSize*2;
        int y = gp.tileSize*9;
        int width = gp.tileSize*6;
        int height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Back", x+24, y+60);

        //PLAYER MONEY WINDOW
        x = gp.tileSize*12;
        y = gp.tileSize*9;
        width = gp.tileSize*6;
        height = gp.tileSize*2;
        drawSubWindow(x, y, width, height);
        g2.drawString("Your Money: " + gp.player.coin, x+24, y+60);

        //DRAW PRICE WINDOW
        int itemIndex = getItemIndexOnSlot(machineSlotCol, machineSlotRow);
        if(itemIndex < machine.inventory.size()){
            x = (int)(gp.tileSize*5.5);
            y = (int)(gp.tileSize*5.5);
            width = (int)(gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = machine.inventory.get(itemIndex).price;
            String text = "" + price;
            x = getXforAlignToRightText(text, gp.tileSize*8-20);
            g2.drawString(text, x, y+34);

            //BUY AN ITEM
            if(gp.keyH.enterPressed == true){
                String itemName = machine.inventory.get(itemIndex).name;
                int itemPrice = machine.inventory.get(itemIndex).price;

                boolean alreadyHasAtout = (
                        (itemName.equals("StaminUp") && gp.player.staminUpAcquired) ||
                        (itemName.equals("QuickRevive") && gp.player.quickReviveAcquired) ||
                        (itemName.equals("Juggernaut") && gp.player.juggernautAcquired) ||
                        (itemName.equals("SpeedCola") && gp.player.speedColaAcquired)
                );

                if(alreadyHasAtout) {
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You already have " + itemName + "!";
                    drawDialogueScreen();
                }
                else if(itemPrice > gp.player.coin){
                    subState = 0;
                    gp.gameState = gp.dialogueState;
                    currentDialogue = "You need more money to buy " + itemName + "!";
                    drawDialogueScreen();
                }
                else {
                    gp.player.coin -= itemPrice;

                    if (itemName.equals("StaminUp")) {
                        gp.player.speed += 2;
                        gp.player.staminUpAcquired = true;
                    } else if (itemName.equals("QuickRevive")) {
                        gp.player.quickReviveAcquired = true;
                    } else if (itemName.equals("Juggernaut")) {
                        gp.player.maxLife +=2;
                        gp.player.life = gp.player.maxLife;
                        gp.player.juggernautAcquired = true;
                    } else if (itemName.equals("SpeedCola")) {
                        gp.player.reloadSpeed = 60;
                        gp.player.speedColaAcquired = true;
                    }
                }
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow){
        int itemIndex = slotCol + slotCol * 5;
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
    }
    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }
    public int getXforAlignToRightText(String text, int tailX){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;
        return x;
    }
    public void drawPlayerInfo(Graphics2D g2){

        //gp.player.life = 3;

        int x = gp.tileSize*1;
        int y = gp.tileSize*11;
        int i =0;

        //DRAW ROUND
        if(gp.roundOver){
            if(gp.counterSpawner<=300){
                    if ((gp.counterSpawner > 30 && gp.counterSpawner <= 60) || (gp.counterSpawner > 90 && gp.counterSpawner <= 120) || (gp.counterSpawner > 150 && gp.counterSpawner <= 180) || (gp.counterSpawner > 210 && gp.counterSpawner <= 240) || (gp.counterSpawner > 270 && gp.counterSpawner <= 300)) {
                        g2.setColor(new Color(79,67,48));
                        g2.setFont(new Font("Arial", Font.PLAIN, 30));
                        g2.drawString(String.valueOf(gp.round), gp.tileSize*1+17 , gp.tileSize*10-15);
                        g2.setColor(new Color(169,31,31));
                        g2.drawString(String.valueOf(gp.round), gp.tileSize*1+15 , gp.tileSize*10-15);
                    }
            }

            if(gp.counterSpawner>300 && gp.counterSpawner<=480){

                int inf=300;
                int sup=318;
                int opa=255;

                for(int w=0;w<10;w++){
                    if (gp.counterSpawner > inf && gp.counterSpawner <= sup) {
                        Color c= new Color(79,67,48,opa);
                        g2.setColor(c);
                        g2.setFont(new Font("Arial", Font.PLAIN, 30));
                        g2.drawString(String.valueOf(gp.round), gp.tileSize*1+17 , gp.tileSize*10-15);
                        c=new Color(169,31,31,opa);
                        g2.setColor(c);
                        g2.drawString(String.valueOf(gp.round), gp.tileSize*1+15 , gp.tileSize*10-15);
                    }
                    inf+=18;
                    sup+=18;
                    opa-=25;
                }
            }
            if(gp.counterSpawner>480 && gp.counterSpawner<=660){
                int inf=480;
                int sup=498;
                int opa=0;

                for(int w=0;w<10;w++) {
                    if (gp.counterSpawner > inf && gp.counterSpawner <= sup) {
                        Color c= new Color(79,67,48,opa);
                        g2.setColor(c);
                        g2.setFont(new Font("Arial", Font.PLAIN, 30));
                        g2.drawString(String.valueOf(gp.round+1), gp.tileSize*1+17 , gp.tileSize*10-15);
                        c=new Color(169,31,31,opa);
                        g2.setColor(c);
                        g2.drawString(String.valueOf(gp.round+1), gp.tileSize*1+15 , gp.tileSize*10-15);
                    }
                    inf += 18;
                    sup += 18;
                    opa += 25;
                }
            }
        }else{
            g2.setColor(new Color(79,67,48));
            g2.setFont(new Font("Arial", Font.PLAIN, 30));
            g2.drawString(String.valueOf(gp.round), gp.tileSize*1+17 , gp.tileSize*10-15);
            g2.setColor(new Color(169,31,31));
            g2.drawString(String.valueOf(gp.round), gp.tileSize*1+15 , gp.tileSize*10-15);
        }

        //DRAW MONEY ON SCREEN
        int moneyX = x;
        int moneyY = y - 35;

        //DRAW DOLLAR SIGN
        BufferedImage coinIcon = new OBJ_Coin(gp).down1;
        g2.drawImage(coinIcon, moneyX, moneyY - gp.tileSize / 2, null);

        // Texte de l'argent
        String moneyText = "" + gp.player.coin;
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString(moneyText, moneyX + gp.tileSize , moneyY +10);

        //DRAW LIFE ON SCREEN
        int l = 0;
        int healthX=x;

        while(l<gp.player.maxLife){
            g2.drawImage(healthEmpty,healthX,gp.tileSize*11-8,null);
            l++;
            healthX+=gp.tileSize-4;
        }

        l = 0;
        healthX=x;

        while(l<gp.player.life){
            if(l==4 || l==5){
                g2.drawImage(addHealth,healthX,gp.tileSize*11-8,null);
            }else{
                g2.drawImage(healthFull,healthX,gp.tileSize*11-8,null);
            }
            l++;
            healthX+=gp.tileSize-4;
        }

        switch(gp.player.life){
            case 3:
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.3F));
                g2.drawImage(screenDamage,0,0,null);
                break;
            case 2:
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6F));
                g2.drawImage(screenDamage,0,0,null);
                break;
            case 1:
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.9F));
                g2.drawImage(screenDamage,0,0,null);
                break;
        }
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));



        g2.setColor((Color.WHITE));

        //Draw max ammo
        x=gp.tileSize/2;
        y=gp.tileSize/2;
        i=0;
        while(i<gp.player.maxAmmo){
            g2.drawImage(ammo_empty,x,y,null);
            i++;
            x+=35;
        }
        //Draw ammo
        x=gp.tileSize/2;
        y=gp.tileSize/2;
        i=0;
        while(i<gp.player.ammo){
            g2.drawImage(ammo_full,x,y,null);
            i++;
            x+=35;
        }

        //DRAW PRESS TO RELOAD
        if(gp.player.ammo<gp.player.maxAmmo && !gp.player.infiniteAmmoIsOn){
            reloadAnimationCounter++;
            if(reloadAnimationCounter<=30){
                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString("Press R to reload !", gp.tileSize-10, gp.tileSize*2);
            }
            if(reloadAnimationCounter>30 && reloadAnimationCounter <= 60){
                g2.setColor(Color.ORANGE);
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString("Press R to reload !", gp.tileSize-10, gp.tileSize*2);
                if(reloadAnimationCounter==60){
                    reloadAnimationCounter=0;
                }
            }
        }

        //DRAW INFINITE AMMO
        if(gp.player.infiniteAmmoIsOn){
            reloadAnimationCounter++;
            if(reloadAnimationCounter<=30){
                g2.setColor(Color.WHITE);
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString("Infinite Ammo !", gp.tileSize-10, gp.tileSize*2);
            }
            if(reloadAnimationCounter>30 && reloadAnimationCounter <= 60){
                g2.setColor(Color.ORANGE);
                g2.setFont(g2.getFont().deriveFont(20F));
                g2.drawString("Infinite Ammo !", gp.tileSize-10, gp.tileSize*2);
                if(reloadAnimationCounter==60){
                    reloadAnimationCounter=0;
                }
            }
        }

        //DRAW SPAWN ZOMBIE

        if(gp.roundOver){
            for(int s=0;s<gp.nbMonster;s++){
                if((gp.coor[s][0]!=999) && (gp.coor[s][1]!=999)){
                    int index= gp.getObjectNull();
                    if(index !=-1){
                        gp.obj[index]=new OBJ_SpawnMark(gp);
                        gp.obj[index].worldX=gp.tileSize*gp.coor[s][0];
                        gp.obj[index].worldY=gp.tileSize*gp.coor[s][1];
                    }
                }
            }
        }

        //UNDRAW SPAWN ZOMBIE
        if(!gp.roundOver){
            for(int f=0;f<gp.obj.length;f++){
                if(gp.obj[f] instanceof OBJ_SpawnMark){
                    gp.obj[f]=null;
                }
            }
        }



        //DRAW BONUS

        //DRAW DOUBLE POINTS
        if(gp.player.doublePointsIsOn){
            if(doublePointsCounter==0){
                nbDoublePoints=gp.player.nbDoublePoints;
            }
            doublePointsCounter++;

            if(gp.player.nbDoublePoints>nbDoublePoints){
                doublePointsCounter=1;
                nbDoublePoints=gp.player.nbDoublePoints;
            }

            if((doublePointsCounter<=840) || (doublePointsCounter>900 && doublePointsCounter<=960) || (doublePointsCounter>1020 && doublePointsCounter<=1080)
                    || (doublePointsCounter>1110 && doublePointsCounter<=1140) || (doublePointsCounter>1155 && doublePointsCounter<=1170)|| (doublePointsCounter>1185 && doublePointsCounter<=1188)
                    || (doublePointsCounter>1191 && doublePointsCounter<=1194) || (doublePointsCounter>1197 && doublePointsCounter<=1199)){
                x=gp.tileSize*12+15;
                y=gp.tileSize*11-15;
                g2.drawImage(doublePoints,x,y,null);
            }
            if(doublePointsCounter==1200){
                doublePointsCounter=0;
                gp.player.doublePointsIsOn=false;
            }
        }else{
            doublePointsCounter=0;
        }

        //DRAW KABOOM
        if(gp.player.kaboomIsOn){

            kaboomCounter++;

            g2.setColor(Color.WHITE);

            if(kaboomCounter==75){
                for (int z =0;z<gp.monster.length;z++) {
                    if(gp.monster[z] instanceof Zombie){
                        gp.player.damageMonster(z,100);
                    }
                }
            }

            int inf=75;
            int sup=90;
            float opa=1F;


            for(int w=0;w<15;w++){
                if(kaboomCounter>inf && kaboomCounter<=sup){
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,opa));
                    g2.fillRect(0, 0, gp.getWidth(), gp.getHeight());
                }
                inf+=15;
                sup+=15;
                opa-=0.06F;
            }

            if(kaboomCounter<300){
                x=gp.tileSize*10+15;
                y=gp.tileSize*11-13;
                g2.drawImage(kaboom,x,y,null);
            }

            if(kaboomCounter==300){
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1F));
                kaboomCounter=0;
                gp.player.kaboomIsOn=false;
            }
        }else{
            kaboomCounter=0;
        }

        //DRAW TIMESTOP
        if(gp.player.timeStopIsOn){
            if(timeStopCounter==0){
                nbTimeStop=gp.player.nbTimeStop;
            }

            for (Entity n : gp.monster) {
                if(n instanceof Zombie){
                    n.speed=0;
                }
            }

            timeStopCounter++;

            if(gp.player.nbTimeStop>nbTimeStop){
                timeStopCounter=1;
                nbTimeStop=gp.player.nbTimeStop;
            }

            if((timeStopCounter<=840) || (timeStopCounter>900 && timeStopCounter<=960) || (timeStopCounter>1020 && timeStopCounter<=1080)
                    || (timeStopCounter>1110 && timeStopCounter<=1140) || (timeStopCounter>1155 && timeStopCounter<=1170)|| (timeStopCounter>1185 && timeStopCounter<=1188)
                    || (timeStopCounter>1191 && timeStopCounter<=1194) || (timeStopCounter>1197 && timeStopCounter<=1199)){
                x=gp.tileSize*14+15;
                y=gp.tileSize*11-15;
                g2.drawImage(timeStop,x,y,null);
            }

            if(timeStopCounter==1200){
                timeStopCounter=0;
                gp.player.timeStopIsOn=false;
                for (Entity n : gp.monster) {
                    if(n instanceof Zombie){
                        switch(n.name){
                            case "Basic Zombie":
                                n.speed =2;
                                break;
                            case "Fat Zombie":
                                n.speed =1;
                                break;
                            case "Puke Zombie":
                                n.speed =1;
                                break;
                        }
                    }
                }
            }
        }else{
            timeStopCounter=0;
        }

        //Draw Infinite Ammo
        if(gp.player.infiniteAmmoIsOn){
            if(infiniteAmmoCounter==0){
                nbInfiniteAmmo=gp.player.nbInfiniteAmmo;
            }

            infiniteAmmoCounter++;

            if(gp.player.nbInfiniteAmmo>nbInfiniteAmmo){
                infiniteAmmoCounter=1;
                nbInfiniteAmmo=gp.player.nbInfiniteAmmo;
            }

            if((infiniteAmmoCounter<=840) || (infiniteAmmoCounter>900 && infiniteAmmoCounter<=960) || (infiniteAmmoCounter>1020 && infiniteAmmoCounter<=1080)
                    || (infiniteAmmoCounter>1110 && infiniteAmmoCounter<=1140) || (infiniteAmmoCounter>1155 && infiniteAmmoCounter<=1170)|| (infiniteAmmoCounter>1185 && infiniteAmmoCounter<=1188)
                    || (infiniteAmmoCounter>1191 && infiniteAmmoCounter<=1194) || (infiniteAmmoCounter>1197 && infiniteAmmoCounter<=1199)){
                x=gp.tileSize*16+15;
                y=gp.tileSize*11-7;
                g2.drawImage(infiniteAmmo,x,y,null);
            }

            if(infiniteAmmoCounter==1200){
                infiniteAmmoCounter=0;
                gp.player.infiniteAmmoIsOn=false;
            }
        }else{
            infiniteAmmoCounter=0;
        }

        //Draw Instakill
        if(gp.player.instakillIsOn){
            if(instakillCounter==0){
                playerProjectileDamageInstakill=gp.player.projectile.attack;
                nbInstakill=gp.player.nbInstakill;
            }
            instakillCounter++;

            if(gp.player.nbInstakill>nbInstakill){
                instakillCounter=1;
                nbInstakill=gp.player.nbInstakill;
            }

            gp.player.attack=100;
            gp.player.projectile.attack=100;

            if((instakillCounter<=840) || (instakillCounter>900 && instakillCounter<=960) || (instakillCounter>1020 && instakillCounter<=1080)
            || (instakillCounter>1110 && instakillCounter<=1140) || (instakillCounter>1155 && instakillCounter<=1170)|| (instakillCounter>1185 && instakillCounter<=1188)
                    || (instakillCounter>1191 && instakillCounter<=1194) || (instakillCounter>1197 && instakillCounter<=1199)){
                x=gp.tileSize*18+15;
                y=gp.tileSize*11-15;
                g2.drawImage(instakill,x,y,null);
            }
            if(instakillCounter==1200){
                instakillCounter=0;
                gp.player.instakillIsOn=false;

                gp.player.attack=gp.player.getAttack();
                gp.player.projectile.attack=playerProjectileDamageInstakill;
                playerProjectileDamageInstakill=0;
            }
        }else{
            instakillCounter=0;
        }

        int accumulatedHeight = 10; // Commencer avec un peu d'espace en haut

        // Position pour StaminUp
        if (gp.player.staminUpAcquired) {
            BufferedImage staminUpImage = new OBJ_StaminUp(gp).down2;
            int staminUpX = gp.screenWidth - staminUpImage.getWidth() - 10;
            g2.drawImage(staminUpImage, staminUpX, accumulatedHeight, null);
            accumulatedHeight += staminUpImage.getHeight();
        }

        // Position pour QuickRevive
        if (gp.player.quickReviveAcquired) {
            BufferedImage quickReviveImage = new OBJ_QuickRevive(gp).down2;
            int quickReviveX = gp.screenWidth - quickReviveImage.getWidth() - 10;
            g2.drawImage(quickReviveImage, quickReviveX, accumulatedHeight, null);
            accumulatedHeight += quickReviveImage.getHeight();
        }

        // Position pour Juggernaut
        if (gp.player.juggernautAcquired) {
            BufferedImage juggernautImage = new OBJ_Juggernaut(gp).down2;
            int juggernautX = gp.screenWidth - juggernautImage.getWidth() - 10;
            g2.drawImage(juggernautImage, juggernautX, accumulatedHeight, null);
            accumulatedHeight += juggernautImage.getHeight();
        }

        // Position pour SpeedCola
        if (gp.player.speedColaAcquired) {
            BufferedImage speedColaImage = new OBJ_SpeedCola(gp).down2;
            int speedColaX = gp.screenWidth - speedColaImage.getWidth() - 10;
            g2.drawImage(speedColaImage, speedColaX, accumulatedHeight, null);
            accumulatedHeight += speedColaImage.getHeight();
        }
    }
}