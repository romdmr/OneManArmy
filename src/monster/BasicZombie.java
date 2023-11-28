package monster;

import main.GamePanel;

public class BasicZombie extends Zombie {

    public BasicZombie(GamePanel gp, int x, int y){
        super(gp,"basicZombie",x,y);
        name="Basic Zombie";
        speed=2;
        maxLife=5;
        life=maxLife;
        attack = 1;
        defense = 0;

        solidArea.x = 12;
        solidArea.y = 10;
        solidArea.width = 24;
        solidArea.height = 38;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
    }
}
