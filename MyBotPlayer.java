
import javafx.application.Platform;

import java.util.List;

public class MyBotPlayer implements BotPlayer{
    Food food;
    MyPlayer myPlayer;
    int x;
    List<Coordinate> coordinates;
    Map map;
    public MyBotPlayer(MyPlayer myPlayer,Map map){
        this.myPlayer = myPlayer;
        this.map = map;
    }

    @Override
    public void feed(Food f) {
        this.food = f;
    }

    private List<Coordinate> execute(){
        Maze maze = new Maze(map.getMap(),new Coordinate(food.getPosition().getY(),food.getPosition().getX()),new Coordinate(myPlayer.getPosition().getX(),myPlayer.getPosition().getY()));
        List<Coordinate> coordinates = bfs(maze);
        return coordinates;
    }

    private List<Coordinate> bfs(Maze maze) {
        BFSMazeSolver bfs = new BFSMazeSolver();
        List<Coordinate> path = bfs.solve(maze);
        List<Coordinate> coordinates = maze.printPath(path);
        maze.reset();
        return coordinates;
    }


    @Override
    public void eat() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (x = 0;x < coordinates.size();x++){
                    Platform.runLater(()->{
                        map.pane.getChildren().remove(myPlayer.getBall());    // removes ball then creates position in the next cell
                        map.pane.add(myPlayer.getBall(),coordinates.get(x).getY(),coordinates.get(x).getX());
                        myPlayer.position = new Position(coordinates.get(x).getX(),coordinates.get(x).getY());
                    });
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void find() {
        this.coordinates = execute();
    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public Position getPosition() {
        return null;
    }
}
