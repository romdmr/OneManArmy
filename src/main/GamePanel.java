package main;

import ai.PathFinder;
import entity.Entity;
import entity.Projectile;
import monster.BasicZombie;
import monster.FatZombie;
import monster.PukeZombie;
import monster.Zombie;
import entity.Player;
import tile.TileManager;
import tile.Tile;
import java.util.Random;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
    // Les settings de la fenetre
    final int originalTileSize = 16; //la taille de standard pour les carrés (perso, tile, etc)
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 car 16*3

    //ici on définit un ratio de 16 par 9
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; //960 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;

    //world setting
    public final int maxWorldCol = 70;
    public final int maxWorldRow = 70;

    //FPS
    int FPS = 60;

    //SYSTEM
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    private boolean isMusicPlaying = false;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;

    //Entity and Object
    public Player player = new Player(this, keyH);
    public Entity monster[]= new Entity[50];
    public ArrayList<Projectile> projectileList = new ArrayList<>();
    public Entity obj[] = new Entity[30]; // 10 slot d'objets qu'on peut prendre
    public Entity npc[] = new Entity[10];
    public int coor[][]= new int[50][2];


    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int pregameState = 5;
    public final int optionsState = 6;
    public final int tradeState = 7;
    public final int gameOverState = 8;
    public final int papState = 9;

    public boolean roundOver =true;
    public int round=0;
    public int nbMonster=1;
    public int counterSpawner=0;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullscreen();

        aSetter.setMonster();
    }

    public void setFullscreen(){
        //GET LOCAL SCREEN DEVICE
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        //GET FULL SCREEN WIDTH AND HEIGHT
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run(){
        double drawInterval = 1000000000/FPS; //deplacement à 0.1666 secs
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1){
                update();
                drawToTempScreen();
                drawToScreen();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        if(gameState == playState){
            //PLAYER
            player.update();

            //ROUND
            checkRound();

            //OBJECT
            for(int i = 0;i < obj.length;i++){
                if(obj[i] != null){
                    if(obj[i].bonus){
                        if(obj[i].objectShowCounter==300){
                            obj[i]=null;
                        }else{
                            obj[i].objectShowCounter++;
                        }
                    }
                }
            }

            //NPC
            for(int i = 0;i < npc.length;i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }

            //MONSTER
            for(int i = 0;i < monster.length;i++){
                if(monster[i] != null){
                    if(monster[i].alive && !monster[i].dying){
                        monster[i].update();
                    }
                    if(!monster[i].alive){
                        monster[i].checkDrop();
                        monster[i]=null;
                    }
                }
            }
        }

        if(gameState == pauseState){

        }

        for(int i=0;i<projectileList.size();i++){
            if(projectileList.get(i)!=null){
                if(projectileList.get(i).alive){
                    projectileList.get(i).update();
                }
                if(!projectileList.get(i).alive){
                    projectileList.remove(i);
                }
            }
        }
    }

    public void drawToTempScreen(){
        //TITLE SCREEN
        if (gameState == titleState){
            ui.draw(g2);
        } else if (gameState == pregameState){
            ui.draw(g2);
        }
        //OTHERS
        else {
            //TILE
            tileM.draw(g2);

            //OBJ
            for(int i = 0; i < obj.length; i++){
                if(obj[i] != null){
                    obj[i].draw(g2);
                }
            }

            //NPC
            for(int i = 0;i < npc.length;i++){
                if(npc[i] != null){
                    npc[i].draw(g2);
                }
            }

            //MONSTER
            for(int i = 0;i < monster.length;i++){
                if(monster[i] != null){
                    monster[i].draw(g2);
                }
            }

            //PROJECTILE
            for(int i = 0;i < projectileList.size();i++){
                if(projectileList.get(i) != null){
                    projectileList.get(i).draw(g2);
                }
            }

            //PLAYER
            player.draw(g2);

            //UI
            ui.draw(g2);
        }
    }

    public void drawToScreen(){
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    public void playMusic(int i){ //music overall
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void playMusicNoLoop(int i){ //music overall
        music.setFile(i);
        music.play();
    }

    public void stopMusic(){
        music.stop();
    }

    public void playSE(int i){ //sound effect
        se.setFile(i);
        se.play();
    }

    public boolean isMusicPlaying() {
        return isMusicPlaying;
    }

    public void setMusicPlaying(boolean isMusicPlaying) {
        this.isMusicPlaying = isMusicPlaying;
    }

    public boolean roundIsOver(){
        boolean res= true;
        for(Entity monster: monster){
            if(monster!=null){
                res=false;
            }
        }
        return res;
    }

    public void checkRound(){

        roundOver=roundIsOver();

        if(roundOver){

            counterSpawner++;

            if(counterSpawner==1){
                for(int i = 0;i<50;i++){
                    coor[i][0]=999;
                    coor[i][1]=999;
                }
            }

            switch(round){
                case 0:
                    if(counterSpawner==300){
                        counterSpawner=0;
                        round+=1;
                        spawnMonster();
                        for(int i=0;i<nbMonster;i++){
                            monster[i]=new BasicZombie(this, coor[i][0],coor[i][1]);
                        }
                    }
                    break;
                case 1: case 2: case 3: case 4: case 5:
                    if(counterSpawner==1){
                        playSE(15);
                        nbMonster++;
                        spawnMonster();
                    }
                    if(counterSpawner==660){
                        counterSpawner=0;
                        round+=1;
                        for(int i=0;i<nbMonster;i++){
                            monster[i]=new BasicZombie(this, coor[i][0],coor[i][1]);
                        }
                    }
                    break;
                case 6: case 7: case 8: case 9: case 10:
                    if(counterSpawner==1){
                        playSE(15);
                        nbMonster++;
                        spawnMonster();
                    }
                    if(counterSpawner==660){
                        counterSpawner=0;
                        round+=1;
                        for(int i=0;i<nbMonster;i++){
                            Random random = new Random();
                            int r = random.nextInt(2) + 1;
                            if(r==1){
                                monster[i]=new BasicZombie(this, coor[i][0],coor[i][1]);
                            }
                            if(r==2){
                                monster[i]=new FatZombie(this, coor[i][0],coor[i][1]);
                            }
                        }
                    }
                    break;
                case 11: case 12: case 13: case 14: case 15:
                    if(counterSpawner==1){
                        playSE(15);
                        nbMonster++;
                        spawnMonster();
                    }
                    if(counterSpawner==660){
                        counterSpawner=0;
                        round+=1;
                        for(int i=0;i<nbMonster;i++){
                            Random random = new Random();
                            int r = random.nextInt(3) + 1;
                            if(r==1){
                                monster[i]=new BasicZombie(this, coor[i][0],coor[i][1]);
                            }
                            if(r==2){
                                monster[i]=new FatZombie(this, coor[i][0],coor[i][1]);
                            }
                            if(r==3){
                                monster[i]=new PukeZombie(this, coor[i][0],coor[i][1]);
                            }
                        }
                    }
                    break;
                default :
                    if(counterSpawner==1){
                        playSE(15);
                        nbMonster++;
                        spawnMonster();
                    }
                    if(counterSpawner==660){
                        counterSpawner=0;
                        round+=1;
                        for(int i=0;i<nbMonster;i++){
                            Random random = new Random();
                            int r = random.nextInt(3) + 1;
                            if(r==1){
                                monster[i]=new BasicZombie(this, coor[i][0],coor[i][1]);
                            }
                            if(r==2){
                                monster[i]=new FatZombie(this, coor[i][0],coor[i][1]);
                            }
                            if(r==3){
                                monster[i]=new PukeZombie(this, coor[i][0],coor[i][1]);
                            }
                        }
                    }
                    break;
            }
        }
    }


    public void spawnMonster(){
        Random random = new Random();
        int index=0;
        while(coor[nbMonster-1][1]==999){
            int col = random.nextInt(68) + 1;
            int row = random.nextInt(68) + 1;
            int tileNum=tileM.mapTileNum[col][row];
            if(!tileM.tile[tileNum].collision){
                System.out.println((tileNum));
                coor[index][0]=col;
                coor[index][1]=row;
                index++;
            }
        }
    }

    public int getObjectNull(){
        for(int f=0;f<obj.length;f++){
            if(obj[f]==null){
                return f;
            }
        }
        return -1;
    }
}