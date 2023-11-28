package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, cutKeyPressed, reloadKeyPressed, tomahawkShotKeyPressed, ePressed;

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_E){
            ePressed = true;
        }
        //TITLE STATE
        if(gp.gameState == gp.titleState){
            titleState(code);
        }
        //PREGAME STATE
        else if (gp.gameState == gp.pregameState) {
            pregameState(code);
        }
        //PLAYSTATE
        else if(gp.gameState == gp.playState){
            playState(code);
        }

        //GAME OVER STATE
        else if (gp.gameState == gp.gameOverState){
            gameOverState(code);
        }

        //PAUSE STATE
        else if(gp.gameState == gp.pauseState){
            pauseState(code);
        }
        
        //OPTIONS STATE
        else if(gp.gameState == gp.optionsState){
            optionsState(code);
        }

        //DIALOGUE STATE
        else if(gp.gameState == gp.dialogueState){
            dialogueState(code);
        }

        //CHARACTER STATE
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }

        //TRADE STATE
        else if(gp.gameState == gp.tradeState){
            tradeState(code);
        }

        //PACK-A-PUNCH STATE
        else if(gp.gameState == gp.papState){
            tradeState(code);
        }
    }

    private void optionsState(int code) {
        if (code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_UP){
            if(gp.ui.commandNum > 0){
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            if(gp.ui.commandNum < 1){
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_LEFT){
            if (gp.ui.commandNum == 0 && gp.music.volumeScale >0){
                gp.music.volumeScale--;
                gp.music.checkVolume();
            }
            if (gp.ui.commandNum == 1 && gp.se.volumeScale >0){
                gp.se.volumeScale--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_RIGHT){
            if (gp.ui.commandNum == 0 && gp.music.volumeScale <5){
                gp.music.volumeScale++;
                gp.music.checkVolume();
            }
            if (gp.ui.commandNum == 1 && gp.se.volumeScale <5){
                gp.se.volumeScale++;
                gp.playSE(9);
            }
        }
    }
    public void titleState(int code){
        if(code == KeyEvent.VK_UP){
            if(gp.ui.commandNum > 0){
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 3;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            if(gp.ui.commandNum < 3){
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0){
                gp.gameState = gp.pregameState;
            }
            if (gp.ui.commandNum == 1){
                //add stats later
            }
            if (gp.ui.commandNum == 2){
                //add credits later
            }
            if (gp.ui.commandNum == 3){
                System.exit(0);
            }
        }
    }
    public void playState(int code){
        if(code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.characterState;
        }
        if(code == KeyEvent.VK_E){
            enterPressed = true;
        }
        if(code == KeyEvent.VK_D){
            cutKeyPressed = true;
        }
        if(code == KeyEvent.VK_S) {
            shotKeyPressed = true;
        }
        if(code == KeyEvent.VK_R) {
            reloadKeyPressed = true;
        }
        if(code == KeyEvent.VK_T) {
            tomahawkShotKeyPressed = true;
        }
    }

    public void gameOverState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.titleState;
        }
        if(code == KeyEvent.VK_UP){
            if(gp.ui.commandNum > 0){
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 1;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            if(gp.ui.commandNum < 1){
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER){
            if (gp.ui.commandNum == 0 ){
                //stats to do
            }
            if (gp.ui.commandNum == 1){
                System.exit(0);
            }
        }
    }
    public void pauseState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }

        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_UP){
            if(gp.ui.commandNum > 0){
                gp.ui.commandNum--;
            } else {
                gp.ui.commandNum = 2;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            if(gp.ui.commandNum < 2){
                gp.ui.commandNum++;
            } else {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
            }
            if (gp.ui.commandNum == 1){
                gp.gameState = gp.optionsState;
            }
            if (gp.ui.commandNum == 2){
                System.exit(0);
            }
        }

    }
    public void dialogueState(int code){
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.playState;
        }
    }
    public void characterState(int code){
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState;
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
        }
        playerInventory(code);
    }

    public void playerInventory(int code){
        if(code == KeyEvent.VK_UP){
            if(gp.ui.playerSlotRow != 0) {
                gp.ui.playerSlotRow--;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            if (gp.ui.playerSlotRow != 3) {
                gp.ui.playerSlotRow++;
            }
        }
        if(code == KeyEvent.VK_LEFT){
            if(gp.ui.playerSlotCol != 0) {
                gp.ui.playerSlotCol--;
            }
        }
        if(code == KeyEvent.VK_RIGHT){
            if (gp.ui.playerSlotCol != 4) {
                gp.ui.playerSlotCol++;
            }
        }
    }

    public void machineInventory(int code){
        if(code == KeyEvent.VK_UP){
            if(gp.ui.machineSlotRow != 0) {
                gp.ui.machineSlotRow--;
            }
        }
        if(code == KeyEvent.VK_DOWN){
            if (gp.ui.machineSlotRow != 3) {
                gp.ui.machineSlotRow++;
            }
        }
        if(code == KeyEvent.VK_LEFT){
            if(gp.ui.machineSlotCol != 0) {
                gp.ui.machineSlotCol--;
            }
        }
        if(code == KeyEvent.VK_RIGHT){
            if (gp.ui.machineSlotCol != 4) {
                gp.ui.machineSlotCol++;
            }
        }
    }
    public void pregameState(int code){
        if(code == KeyEvent.VK_SPACE){
            gp.gameState = gp.playState;
            gp.playMusic(0);
        }
    }

    public void tradeState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(gp.ui.subState == 0){
            if(code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
            }
        }
        if(gp.ui.subState == 1){
            machineInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }

    public void papState(int code){
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }
        if(gp.ui.subState == 0){
            if(code == KeyEvent.VK_UP){
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0){
                    gp.ui.commandNum = 1;
                }
            }
            if(code == KeyEvent.VK_DOWN){
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 1){
                    gp.ui.commandNum = 0;
                }
            }
        }
        if(gp.ui.subState == 1){
            machineInventory(code);
            if(code == KeyEvent.VK_ESCAPE){
                gp.ui.subState = 0;
            }
        }
    }

    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_E){
            ePressed = false;
        }

        if(code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_S){
            shotKeyPressed = false;
        }
        if(code == KeyEvent.VK_D){
            cutKeyPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            shotKeyPressed = false;
        }
        if(code == KeyEvent.VK_R) {
            reloadKeyPressed = false;
        }
        if(code == KeyEvent.VK_T) {
            tomahawkShotKeyPressed = false;
        }
    }
}