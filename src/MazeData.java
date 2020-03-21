
public class MazeData {

    public static final char ROAD = ' ';
    public static final char WALL = '#';

    private int N, M;
    public char[][] maze;
    public boolean[][] visited;
    public boolean[][] inMist;
    public boolean[][] path;

    private int entranceX, entranceY;
    private int exitX, exitY;


    //行列一定是奇数 :生成原理 先预先在奇数坐标处设好路 然后不断打破路与路之间的墙 最后生成迷宫
    public MazeData(int N, int M) {
        if (N % 2 == 0 || M % 2 == 0)
            throw new IllegalArgumentException("NO");
        this.M = M;
        this.N = N;
        maze = new char[N][M];
        visited = new boolean[N][M];
        inMist = new boolean[N][M];
        path = new boolean[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i % 2 == 1 && j % 2 == 1)
                    maze[i][j] = ROAD;
                else
                    maze[i][j] = WALL;

                inMist[i][j] = true;
            }
        }

        entranceX = 1;
        entranceY = 0;
        exitX = N - 2;
        exitY = M - 1;

        maze[entranceX][entranceY] = ROAD;
        maze[exitX][exitY] = ROAD;
    }

    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public int getEntranceX() {
        return entranceX;
    }

    public int getEntranceY() {
        return entranceY;
    }

    public int getExitX() {
        return exitX;
    }

    public int getExitY() {
        return exitY;
    }

    public boolean inArea(int x, int y) {
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public void openMist(int x, int y) {
        if(!inArea(x,y))
            throw new IllegalArgumentException("Index Error");

        for (int i = x-1; i <=x+1 ; i++) {
            for (int j = y-1; j <=y+1 ; j++) {
                if(inArea(i,j))
                    inMist[i][j]=false;
            }
        }
    }
}
