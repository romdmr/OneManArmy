package object;
import main.GamePanel;
import entity.Entity;

public class OBJ_Coin extends Entity{

    public OBJ_Coin(GamePanel gp){

        super(gp);

        name="Coin Icon";
        down1 = setup("/object/money",gp.tileSize,gp.tileSize);
    }
}