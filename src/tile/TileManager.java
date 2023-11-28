package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gp){
        this.gp = gp;
        tile = new Tile[999];
        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap("/maps/worldOMA2.txt");
    }

    public void getTileImage(){

        /*Dirt, Water, ...*/
        setup(0, "dirt_0", false);
        setup(17, "water_17", true);
        setup(11, "dirt_11", false);
        setup(20, "riverbank_20", true);
        setup(69, "bloody_dirt_1", false);
        setup(70, "bloody_dirt_2", false);
        setup(71, "bloody_dirt_3", false);
        setup(72, "bloody_dirt_4", false);

        /*Road & Bridge*/
        setup(1, "road_1", false);
        setup(2, "road_2", false);
        setup(3, "road_3", false);
        setup(4, "road_4", false);
        setup(5, "road_5", false);
        setup(6, "road_6", false);
        setup(7, "road_7", false);
        setup(8, "road_8", false);
        setup(9, "road_9", false);
        setup(10, "road_10", false);
        setup(12, "road_12", false);
        setup(13, "road_13", false);
        setup(14, "road_14", false);
        setup(15, "road_15", false);
        setup(16, "road_16", false);
        setup(18, "bridge_18", true);
        setup(19, "bridge_19", true);
        setup(21, "road_21", false);
        setup(22, "road_22", false);
        setup(23, "road_23", false);

        /*Cars*/
        setup(24, "car_1", true);
        setup(25, "car_2", true);
        setup(63, "car_3", true);
        setup(64, "car_4", true);
        setup(26, "pickup_1", true);
        setup(27, "pickup_2", true);

        /*Teleportation portal*/
        setup(28, "gate_1", false);
        setup(29, "gate_2", false);
        setup(30, "gate_3", false);
        setup(31, "gate_4", false);

        /*House 1*/
        setup(32, "house_1_1", true);
        setup(33, "house_1_2", true);
        setup(34, "house_1_3", true);
        setup(35, "house_1_4", true);
        setup(36, "house_1_5", true);
        setup(37, "house_1_6", true);
        setup(38, "house_2_1", true);
        setup(39, "house_2_2", true);
        setup(40, "house_2_3", true);
        setup(41, "house_2_4", true);
        setup(42, "house_2_5", true);
        setup(43, "house_2_6", true);
        setup(44, "house_3_1", true);
        setup(45, "house_3_2", true);
        setup(46, "house_3_3", true);
        setup(47, "house_3_4", true);
        setup(48, "house_3_5", true);
        setup(49, "house_3_6", true);

        /*Break Bridge & Road*/
        setup(50, "break_bridge_1", true);
        setup(51, "break_bridge_2", true);
        setup(52, "break_bridge_3", true);
        setup(53, "break_bridge_4", true);
        setup(54, "break_road_1_left", true);
        setup(55, "break_road_2_left", true);
        setup(56, "break_road_3_left", true);
        setup(57, "break_road_1_right", true);
        setup(58, "break_road_2_right", true);
        setup(59, "break_road_3_right", true);
        setup(60, "break_road_top_1", true);
        setup(61, "break_road_top_2", true);
        setup(62, "break_road_top_3", true);

        /*Top of Machine (QuickRevive, StaminUp...)*/
        setup(65, "staminup_machine_top", true);
        setup(66, "quickrevive_machine_top", true);
        setup(67, "jugg_machine_top", true);
        setup(68, "speedcola_machine_top", true);

        /*Easter Egg*/
        setup(73, "tomb", true);
        setup(74, "tronc", true);
        setup(75, "wood_box", true);
        setup(76, "tomb_empty", true);
        setup(77, "tronc_empty", true);
        setup(78, "wood_box_empty", true);

    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void loadMap(String filePath){
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int col = 0;
            int row = 0;

            while(col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while(col < gp.maxWorldCol){

                    String numbers[] = line.split(" ");

                    int num = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }
            br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2){
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
                g2.drawImage(tile[tileNum].image, screenX, screenY,null);
            }
            worldCol++;

            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
