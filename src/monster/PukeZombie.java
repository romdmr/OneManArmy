package monster;

import main.GamePanel;
import object.OBJ_ZombiePuke;

public class PukeZombie extends Zombie {

    public PukeZombie(GamePanel gp, int x, int y){
        super(gp,"pukeZombie",x,y);
        name="Puke Zombie";
        speed=1;
        maxLife=15;
        life=maxLife;

        attack=1;
        defense=0;
        projectile=new OBJ_ZombiePuke(gp);


        solidArea.x = 5;
        solidArea.y = 10;
        solidArea.width = 38;
        solidArea.height = 38;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
    }

}
