package monster;

import main.GamePanel;

public class FatZombie extends Zombie {

    public FatZombie(GamePanel gp, int x, int y){
        super(gp,"fatZombie",x,y);
        name="Fat Zombie";
        speed=1;
        maxLife=10;
        life=maxLife;

        attack=1;
        defense=0;


        solidArea.x = 0;
        solidArea.y = 10;
        solidArea.width = 48;
        solidArea.height = 38;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
    }

}
