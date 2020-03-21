import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AlgoVisualizer {

    private final static int blockSize = 8;
    private final static int DELAY = 5;
    private final static int d[][] = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    private MazeData data;        // 数据
    private AlgoFrame frame;    // 视图


    public AlgoVisualizer(int N, int M) {

        // 初始化数据
        data = new MazeData(N, M);
        // 初始化视图
        int sceneHeight = data.getN() * blockSize;
        int sceneWidth = data.getM() * blockSize;
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame("Maze", sceneWidth, sceneHeight);
            frame.addKeyListener(new AlgoKeyListener());

            new Thread(() -> {
                run();
            }).start();
        });
    }

    // 动画逻辑
    private void run() {
        setData(-1, -1);

        RandomQueue<Position> queue = new RandomQueue<>();
        Position first = new Position(data.getEntranceX(), data.getEntranceY() + 1);
        queue.add(first);
        data.visited[first.getX()][first.getY()] = true;
        data.openMist(first.getX(),first.getY());

        while (!queue.isEmpty()) {
            Position curPos = queue.remove();
            for (int i = 0; i < 4; i++) {
                int newX = curPos.getX() + d[i][0] * 2;
                int newY = curPos.getY() + d[i][1] * 2;

                if (data.inArea(newX, newY) && !data.visited[newX][newY]) {
                    queue.add(new Position(newX, newY));
                    data.visited[newX][newY] = true;
                    data.openMist(newX,newY);
                    setData(curPos.getX() + d[i][0], curPos.getY() + d[i][1]);
                }
            }

        }

        setData(-1, -1);
    }

    private class AlgoKeyListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent event){
            if(event.getKeyChar() == ' '){
                for(int i = 0 ; i < data.getN() ; i ++)
                    for(int j = 0 ; j < data.getM() ; j ++)
                        data.visited[i][j] = false;

                new Thread(() -> {
                    go(data.getEntranceX(), data.getEntranceY());
                }).start();
            }
        }
    }

    private void setData(int x, int y) {
        if (data.inArea(x, y))
            data.maze[x][y] = MazeData.ROAD;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }


    private boolean go(int x, int y){

        if(!data.inArea(x,y))
            throw new IllegalArgumentException("x,y are out of index in go function!");

        data.visited[x][y] = true;
        setPathData(x, y, true);

        if(x == data.getExitX() && y == data.getExitY())
            return true;

        for(int i = 0 ; i < 4 ; i ++){
            int newX = x + d[i][0];
            int newY = y + d[i][1];
            if(data.inArea(newX, newY) &&
                    data.maze[newX][newY] == MazeData.ROAD &&
                    !data.visited[newX][newY])
                if(go(newX, newY))
                    return true;
        }

        // 回溯
        setPathData(x, y, false);

        return false;
    }

    private void setPathData(int x, int y, boolean isPath){
        if(data.inArea(x, y))
            data.path[x][y] = isPath;

        frame.render(data);
        AlgoVisHelper.pause(DELAY);
    }


    public static void main(String[] args) {
        int N = 101;
        int M = 101;
        AlgoVisualizer vis = new AlgoVisualizer(N, M);
    }
}