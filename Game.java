package Snacks;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class Game {
        static final int WIDTH=50;
        static final int HEIGTH=10;
        static char[][] ditu=null;
        static LinkedList<Snack> list=new LinkedList<>();
        public static void initDitu(){
            ditu=new char[HEIGTH][WIDTH];
            for (int i = 0; i <HEIGTH ; i++) {
                for (int j = 0; j <WIDTH ; j++) {
                    if(i==0||i==HEIGTH-1||j==0||j==WIDTH-1){
                        ditu[i][j]='*';
                    }else {ditu[i][j]=' ';}
                }
            }
        }
        public static void showditu(){
            for (int i = 0; i < HEIGTH; i++) {
                for (int j = 0; j <WIDTH ; j++) {
                    System.out.print(ditu[i][j]+" ");
                }
                System.out.println();
            }
        }
        public static void initsnack(){
            int x=WIDTH/2;
            int y=HEIGTH/2;
            list.add(new Snack(x,y));
            list.add(new Snack(x+1,y));
            list.add(new Snack(x+2,y));
        }
    public static void showsnack(){
            Snack head=list.getFirst();
            ditu[head.getY()][head.getX()]='◉';
        for (int i = 1; i <list.size() ; i++) {
            ditu[list.get(i).getY()][list.get(i).getX()]='■';
        }
    }

    public static void main(String[] args) {
        Scanner sca=new Scanner(System.in);
        initDitu();
        initsnack();
        showsnack();
        showfoot();
        showditu();
        while(true){
            System.out.println("输入移动的方向和步数，-1：左，1：右，-2：上，2：下");
            int direction=sca.nextInt();
            int size=sca.nextInt();
            run(direction,size);
        }
    }

    private static void ganmeover() {
        Snack head=list.getFirst();
            if(ditu[head.getY()][head.getX()]=='*'||ditu[head.getY()][head.getX()]=='■'){
                System.out.println("你GG了");
                System.exit(0);
            }
    }

    private static boolean eat() {
            Snack head=list.getFirst();
            if(ditu[head.getY()][head.getX()]=='◈'){
                return true;
            }
            return false;
    }

    public static void run(int direction,int size) {
            if(direction==-1||direction==1){
                for (int i = 0; i <size ; i++) {
                    Snack snack1=list.getLast();
                    Snack snack=list.getFirst();
                    int x=snack.getX()+direction;
                    Snack snack2=new Snack(x,snack.getY());
                    list.addFirst(snack2);
                    ganmeover();
                    if (eat()){
                        showfoot();
                        System.out.println("吃到东西了");
                    }else {
                        list.removeLast();
                        ditu[snack1.getY()][snack1.getX()] = ' ';
                    }

                    showsnack();
                    showditu();
                }
            }else{
                int condition=0;
                if(direction>0){
                    condition=1;
                }else{
                    condition=-1;
                }
                for (int i = 0; i <size ; i++) {
                    Snack snack1=list.getLast();
                    Snack snack=list.getFirst();
                    int y=snack.getY()+condition;
                    Snack snack2=new Snack(snack.getX(),y);
                    list.addFirst(snack2);
                    ganmeover();
                    if (eat()){
                        showfoot();
                        System.out.println("吃到东西了");

                    }else {
                        list.removeLast();
                        ditu[snack1.getY()][snack1.getX()] = ' ';
                    }
                    showsnack();
                    showditu();
                }
            }


    }

    private static void showfoot() {
            while(true) {
                int x = new Random().nextInt(WIDTH - 1);
                int y =new Random().nextInt(HEIGTH - 1);
                if (ditu[y][x] != '*' && ditu[y][x] != '■' && ditu[y][x] != '◉') {
                    ditu[y][x] = '◈';
                    break;
                }
            }
    }
}
