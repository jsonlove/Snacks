package Snacks;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class View extends JPanel implements KeyListener {
    volatile int flag=0;
    static final int HEIGTH = 20;
    static final int WIDTH = 30;
    static char[][] ditu = null;
    static Snack head = null;
    static int x = 0;
    static View view;
    static LinkedList<Snack> list = new LinkedList<>();
    static Image snackhead;
    static Image snackbody;
    public static void initDitu() {
        ditu = new char[HEIGTH][WIDTH];
        for (int i = 0; i < HEIGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (i == 0 || i == HEIGTH - 1 || j == 0 || j == WIDTH - 1) {
                    ditu[i][j] = '*';
                } else {
                    ditu[i][j] = ' ';
                }
            }
        }
    }

    public static void showditu() {
        for (int i = 0; i < HEIGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(ditu[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void initsnack() {
        int x = WIDTH / 2;
        int y = HEIGTH / 2;
        list.add(new Snack(x, y));
        list.add(new Snack(x + 1, y));
        list.add(new Snack(x + 2, y));
    }

    public static void showsnack() {
        head = list.getFirst();
        ditu[head.getY()][head.getX()] = '◉';
        for (int i = 1; i < list.size(); i++) {
            ditu[list.get(i).getY()][list.get(i).getX()] = '■';
        }
    }

    @Override
    public void paint(Graphics g) {
        //地图 食物
        for (int i = 0; i < HEIGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (ditu[i][j] == '*') {
                    g.setColor(Color.red);
                } else if (ditu[i][j] == '1') {
                    g.setColor(new Color(139, 0, 0));
                } else {
                    g.setColor(Color.white);
                }
                g.fill3DRect(20 * j, 20 * i, 20, 20, true);
            }
        }
        //蛇
        try {
            snackhead= ImageIO.read(new File("G:\\workspace\\Test\\src\\Snacks\\tou.png"));
            snackbody= ImageIO.read(new File("G:\\workspace\\Test\\src\\Snacks\\body.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                g.setColor(new Color(0,0,0,0));
                g.drawImage(snackhead,20 * list.get(i).getX(), 20 * list.get(i).getY(),20,20,this);
            } else {
                g.setColor(new Color(0,0,0,0));
                g.drawImage(snackbody,20 * list.get(i).getX(), 20 * list.get(i).getY(),20,20,this);
            }
            g.fill3DRect(20 * list.get(i).getX(), 20 * list.get(i).getY(), 20, 20, true);

        }


    }

    public static void main(String[] args) {
        view = new View();
        JFrame jf = new JFrame();
        jf.setSize(WIDTH * 20 + 16, HEIGTH * 20 + 40);
        jf.add(view);
        jf.setVisible(true);
        jf.addKeyListener(view);
        JButton jb1=new JButton("开始游戏");
        jb1.setBounds(0,0,100,30);
        jb1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        jf.add(jb1);

//        jf.addKeyListener(new KeyListener() {
//            public void keyTyped(KeyEvent e) {
//            }
//
//            public void keyPressed(KeyEvent e) {
//            }
//
//            public void keyReleased(KeyEvent e) {
//                    switch (e.getKeyCode()) {
//                        case KeyEvent.VK_UP:
//                                run(-2);
//                            break;
//                        case KeyEvent.VK_DOWN:
//                            run(2);
//                            break;
//                        case KeyEvent.VK_LEFT:
//                            run(-1);
//                            break;
//                        case KeyEvent.VK_RIGHT:
//                            run(1);
//                            break;
//                    }
//                    if (eat()) {
//                        System.out.println(11);
//                        initfoot();
//                    }
//                    view.repaint();
//
//            }
 //       });
        initDitu();
        initsnack();
        showsnack();
        initfoot();
        //showditu();
//        while(true){
//            System.out.println("输入移动的方向和步数，-1：左，1：右，-2：上，2：下");
//            int direction=sca.nextInt();
//            int size=sca.nextInt();
//            run(direction,size);
//        }
    }

    private static void ganmeover() {
        Snack head = list.getFirst();

        if (ditu[head.getY()][head.getX()] == '*' || ditu[head.getY()][head.getX()] == '■') {
            int i=JOptionPane.showConfirmDialog(null,"是否结束游戏","友情提示",JOptionPane.YES_NO_OPTION);
            if (i==JOptionPane.YES_OPTION){
                System.exit(0);
            }else{
                System.exit(0);
            }
        }
    }

    private static boolean eat() {
        Snack head = list.getFirst();
        if (ditu[head.getY()][head.getX()] == '1') {
            return true;
        }
        return false;
    }


    public static void  runs(int direction) {

            if (direction == -1 || direction == 1) {
                Snack snack1 = list.getLast();
                Snack snack = list.getFirst();
                int x = snack.getX() + direction;
                Snack snack2 = new Snack(x, snack.getY());
                list.addFirst(snack2);
                ganmeover();
                if (!eat()) {
                    list.removeLast();
                    ditu[snack1.getY()][snack1.getX()] = ' ';
                   // showditu();
                    showsnack();
                }
            } else {
                int condition = 0;
                if (direction > 0) {
                    condition = 1;
                } else {
                    condition = -1;
                }
                Snack snack1 = list.getLast();
                Snack snack = list.getFirst();
                int y = snack.getY() + condition;
                Snack snack2 = new Snack(snack.getX(), y);
                list.addFirst(snack2);
                ganmeover();
                if (!eat()) {
                    list.removeLast();
                    ditu[snack1.getY()][snack1.getX()] = ' ';
                    //showditu();
                    showsnack();
                }
            }
            x = direction;

    }

    private static void initfoot() {
        while (true) {
            int x = new Random().nextInt(WIDTH - 1);
            int y = new Random().nextInt(HEIGTH - 1);
            if (ditu[y][x] != '*' && ditu[y][x] != '■' && ditu[y][x] != '◉') {
                ditu[y][x] = '1';
                break;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public  void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                if(flag==-2)break;
                if(flag-2==0)break;
                flag=-2;
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        while(flag==-2){
                            try {
                                runs(-2);
                                view.repaint();
                                if(eat()){
                                    initfoot();
                                }
                                if(list.size()<5) {
                                    Thread.sleep(500);
                                }else if(list.size()<13){
                                    Thread.sleep(200);
                                }else{
                                    Thread.sleep(50);
                                }

                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
                //run(-2);
                break;
            case KeyEvent.VK_DOWN:
                if(flag==2)break;
                if(flag+2==0)break;
                flag=2;
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        while(flag==2){
                            try {
                                runs(2);
                                view.repaint();
                                if(eat()){
                                    initfoot();
                                }
                                if(list.size()<5) {
                                    Thread.sleep(500);
                                }else if(list.size()<13){
                                    Thread.sleep(200);
                                }else{
                                    Thread.sleep(50);
                                }
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
               // run(2);
                break;
            case KeyEvent.VK_LEFT:
                if(flag==-1)break;
                if(flag-1==0)break;
                flag=-1;
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        while(flag==-1){
                            try {
                                runs(-1);
                                view.repaint();
                                if(eat()){
                                    initfoot();
                                }
                                if(list.size()<5) {
                                    Thread.sleep(500);
                                }else if(list.size()<13){
                                    Thread.sleep(200);
                                }else{
                                    Thread.sleep(50);
                                }
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
                //run(-1);
                break;
            case KeyEvent.VK_RIGHT:
                if(flag==1)break;
                if(flag+1==0)break;

                flag=1;
                new Thread(new Runnable() {
                    @Override
                    public synchronized void run() {
                        while(flag==1){
                            try {
                                runs(1);
                                view.repaint();
                                if(eat()){
                                    initfoot();
                                }
                                if(list.size()<5) {
                                    Thread.sleep(500);
                                }else if(list.size()<13){
                                    Thread.sleep(200);
                                }else{
                                    Thread.sleep(50);
                                }

                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
                //run(1);
                break;
        }
    }

    @Override
    public  void keyReleased(KeyEvent e) {
//        int i=0;
//       while(i++<3) {
//           if(status==0)break;
//            run(status);
//            if (eat()) {
//                System.out.println(11);
//                initfoot();
//            }
//            paint(this.getGraphics());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
//
//        }
    }
}
