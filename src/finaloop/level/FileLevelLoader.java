package finaloop.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import finaloop.board;
import finaloop.game;
import finaloop.mapObjects.LayeredEntity;
import finaloop.mapObjects.character.player;
import finaloop.mapObjects.character.enemy.balloon;
import finaloop.mapObjects.tile.Grass;
import finaloop.mapObjects.tile.Portal;
import finaloop.mapObjects.tile.Wall;
import finaloop.mapObjects.tile.destroyable.Brick;
import finaloop.mapObjects.tile.item.BombItem;
import finaloop.mapObjects.tile.item.FlameItem;
import finaloop.mapObjects.tile.item.SpeedItem;
;
import finaloop.graphics.Screen;
import finaloop.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

    private static char[][] _map;

    public FileLevelLoader(board board, int level)  {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) {
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrays = list.get(0).trim().split(" ");
        _level = Integer.parseInt(arrays[0]);
        _height = Integer.parseInt(arrays[1]);
        _width = Integer.parseInt(arrays[2]);
        _map = new char[_height][_width];
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                _map[i][j] = list.get(i + 1).charAt(j);
            }
        }
    }

    @Override
    public void createEntities() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int pos = x + y * getWidth();
                char c = _map[y][x];
                switch (c) {

                    case ' ':
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;

                    case '#':
                        _board.addEntity(pos, new Wall(x, y, Sprite.wall));
                        break;

                    case 'x':
                        _board.addEntity(pos, new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new Portal(x, y, _board, Sprite.portal),
                                new Brick(x, y, Sprite.brick)));
                        break;

                    case '*':
                        _board.addEntity(x + y * _width,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new Brick(x, y, Sprite.brick)
                                )
                        );
                        break;

                    case 'p':
                        _board.addCharacter(new player(coord.tileToPixel(x), coord.tileToPixel(y) + game.TILES_SIZE, _board));
                        Screen.setOffset(0, 0);
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;


                    case '1':
                        _board.addCharacter(new balloon(coord.tileToPixel(x), coord.tileToPixel(y) + game.TILES_SIZE, _board));
                        _board.addEntity(x + y * _width, new Grass(x, y, Sprite.grass));
                        break;

                    case 'b':
                        LayeredEntity layer = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new BombItem(x, y, Sprite.powerup_bombs),
                                new Brick(x, y, Sprite.brick));
                        _board.addEntity(pos, layer);
                        break;

                    case 's':
                        layer = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new SpeedItem(x, y, Sprite.powerup_speed),
                                new Brick(x, y, Sprite.brick));
                        _board.addEntity(pos, layer);
                        break;

                    case 'f':
                        layer = new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new FlameItem(x, y, Sprite.powerup_flames),
                                new Brick(x, y, Sprite.brick));
                        _board.addEntity(pos, layer);
                        break;

                    default:
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;

                }
            }
        }
    }
}
