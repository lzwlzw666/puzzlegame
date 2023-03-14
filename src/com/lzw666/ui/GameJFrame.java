package com.lzw666.ui;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GameJFrame extends JFrame implements KeyListener, ActionListener {
    //创建二维数组,用于打乱图片位置
    int[][] data = new int[4][4];

    //记录空白方块在二维数组中的位置
    int x = 0;
    int y = 0;

    //记录图片路径
    String path = "..\\puzzlegame\\image\\animal\\animal3\\";

    //定义一个二维数组,存储正确的数据
    int[][] win = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
    };

    //定义变量,记录步数
    int step = 0;

    //随机数,用于更换图片
    Random r = new Random();
    //定义变量,用于随机数范围
    int imageNum;
    //记录鼠标点击事件对象
    Object obj;

    //创建选项下面的条目对象
    // 创建JMenuItem的对象
    JMenuItem girl = new JMenuItem("美女");
    JMenuItem animal = new JMenuItem("动物");
    JMenuItem sport = new JMenuItem("运动");
    JMenuItem repalyItem = new JMenuItem("重新游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem closeItem = new JMenuItem("关闭游戏");
    JMenuItem accountItem = new JMenuItem("个人微信");
    //1.创建菜单JMenuBar的对象
    JMenuBar jMenuBar = new JMenuBar();
    //2.创建菜单上面的两个选项JMenu
    JMenu functionJMenu = new JMenu("功能");
    JMenu aboutJMenu = new JMenu("关于我们");

    //创建更换图片
    JMenu changeImage = new JMenu("更换图片");

    public GameJFrame() {
        //初始化界面
        initJFrame();

        //初始化菜单
        initJMenuBar();

        //初始化数据(打乱)
        initData();

        //初始化图片
        initImage();

        //显示界面
        this.setVisible(true);
    }


    private void initData() {
        int[] tempArr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        //打乱一维数组
        Random r = new Random();
        for (int i = 0; i < tempArr.length; i++) {
            int index = r.nextInt(tempArr.length);
            int temp = tempArr[i];
            tempArr[i] = tempArr[index];
            tempArr[index] = temp;
        }
        //将打乱后的一维数组赋值给二维数组
        for (int i = 0; i < tempArr.length; i++) {
            if (tempArr[i] == 0) {
                x = i / 4;
                y = i % 4;
            }
            data[i / 4][i % 4] = tempArr[i];
        }
    }

    //初始化图片
    //添加图片时,要按照二维数组中的数据来添加
    private void initImage() {
        //清空原本已出现的图片
        this.getContentPane().removeAll();

        //先加载的图片在上方
        //添加胜利图标
        if (victory()) {
            JLabel winJLablel = new JLabel(new ImageIcon("..\\puzzlegame\\image\\win.png"));
            winJLablel.setBounds(203, 283, 197, 73);
            this.getContentPane().add(winJLablel);
        }

        //添加计步器
        JLabel stepCount = new JLabel("步数 :  " + step);
        stepCount.setBounds(50, 30, 100, 20);
        this.getContentPane().add(stepCount);
        //随机打乱图片
        //外循环 --- 把内循环重复执行了4次。
        for (int i = 0; i < 4; i++) {
            //内循环 --- 表示在一行添加4张图片
            for (int j = 0; j < 4; j++) {
                int number = data[i][j];
                //创建一个JLabel的对象（管理容器）
                ImageIcon icon = new ImageIcon(path + number + ".jpg");
                //创建一个JLabel的对象(管理容器)
                JLabel jLabel = new JLabel(icon);
                //指定图片位置
                jLabel.setBounds(105 * j + 83, 105 * i + 134, 105, 105);
                //给图片添加边框
                jLabel.setBorder(new BevelBorder(BevelBorder.LOWERED));
                //把管理容器添加到界面中
                this.getContentPane().add(jLabel);
                //添加完一次后,number自增,下一次添加后面一张图片
                number++;
            }
        }
        //添加背景图片
        JLabel bankground = new JLabel(new ImageIcon("..\\puzzlegame\\image\\background.png"));
        bankground.setBounds(40, 40, 508, 560);
        //把背景图片添加到界面当中
        this.getContentPane().add(bankground);

        //刷新界面
        this.getContentPane().repaint();
    }

    private void initJMenuBar() {
        //创建菜单并添加到界面当中
        // 把美女，动物，运动添加到更换图片当中
        changeImage.add(girl);
        changeImage.add(animal);
        changeImage.add(sport);

        //把更换图片，重新游戏，重新登录，关闭游戏添加到功能当中
        functionJMenu.add(changeImage);
        functionJMenu.add(repalyItem);
        functionJMenu.add(reLoginItem);
        functionJMenu.add(closeItem);

        //把公众号添加到关于我们当中
        aboutJMenu.add(accountItem);

        //把功能，关于我们添加到JMenuBar当中
        jMenuBar.add(functionJMenu);
        jMenuBar.add(aboutJMenu);

        //绑定监听事件
        girl.addActionListener(this);
        animal.addActionListener(this);
        sport.addActionListener(this);
        repalyItem.addActionListener(this);
        reLoginItem.addActionListener(this);
        closeItem.addActionListener(this);
        accountItem.addActionListener(this);

        //把整个菜单JMenuBar添加到整个界面当中
        this.setJMenuBar(jMenuBar);
    }

    private void initJFrame() {
        //设置界面宽高
        this.setSize(603, 680);
        //设置界面标题
        this.setTitle("拼图单机版 v1.0");
        //设置界面置顶
        this.setAlwaysOnTop(true);
        //设置界面居中
        this.setLocationRelativeTo(null);
        //设置关闭模式
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //取消默认的居中放置,按照X,Y轴添加组件
        this.setLayout(null);
        //给整个界面添加键盘监听事件
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == 65) {
            //把界面中图片清除
            this.getContentPane().removeAll();
            //加载完整图片
            JLabel all = new JLabel(new ImageIcon(path + "all.jpg"));
            all.setBounds(83, 134, 420, 420);
            this.getContentPane().add(all);

            //添加背景图片
            JLabel bankground = new JLabel(new ImageIcon("..\\puzzlegame\\image\\background.png"));
            bankground.setBounds(40, 40, 508, 560);
            //把背景图片添加到界面当中
            this.getContentPane().add(bankground);

            //刷新界面
            this.getContentPane().repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        //判断游戏是否胜利,如果胜利,此方法直接结束
        if (victory()) {
            return;
        }

        //对上下左右进行判断
        //左:37 上:38 右:39 下:40
        int code = e.getKeyCode();
        if (code == 37) {
            System.out.println("向左移动");
            if (y == 3) {
                return;
            }
            //逻辑
            //把空白方块右方的数字往在移动
            //x,y 代表空白方块
            //x,y+1
            data[x][y] = data[x][y + 1];
            data[x][y + 1] = 0;
            y++;
            //每移动一次,步数加一
            step++;
            //调用方法加载图片
            initImage();
        } else if (code == 38) {
            System.out.println("向上移动");
            if (x == 3) {
                return;
            }
            //逻辑
            //把空白方块下方的数字往上移动
            //x,y 代表空白方块
            //x+1,y
            data[x][y] = data[x + 1][y];
            data[x + 1][y] = 0;
            x++;
            //每移动一次,步数加一
            step++;
            //调用方法加载图片
            initImage();
        } else if (code == 39) {
            System.out.println("向右移动");
            if (y == 0) {
                return;
            }
            //逻辑
            //把空白方块左方的数字往右移动
            //x,y 代表空白方块
            //x,y-1
            data[x][y] = data[x][y - 1];
            data[x][y - 1] = 0;
            y--;
            //每移动一次,步数加一
            step++;
            //调用方法加载图片
            initImage();
        } else if (code == 40) {
            System.out.println("向下移动");
            if (x == 0) {
                return;
            }
            //逻辑
            //把空白方块上方的数字往下移动
            //x,y 代表空白方块
            //x-1,y
            data[x][y] = data[x - 1][y];
            data[x - 1][y] = 0;
            x--;
            //每移动一次,步数加一
            step++;
            //调用方法加载图片
            initImage();
        } else if (code == 65) {
            initImage();
        } else if (code == 87) {
            data = new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 0}
            };
            initImage();
        }

    }

    public boolean victory() {
        //遍历比较data数组和win数组
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                //若有一个值不同,返回false
                if (data[i][j] != win[i][j]) {
                    return false;
                }
            }
        }
        //循环结束,值全一样,返回true
        return true;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //获取当前被点击的条目对象
        obj = e.getSource();
        if (obj == repalyItem) {
            System.out.println("重新游戏");
            //计步器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        } else if (obj == reLoginItem) {
            System.out.println("重新登录");
            //关闭当前游戏界面
            this.setVisible(false);
            //打开登录界面
            new LoginJFrame();
        } else if (obj == closeItem) {
            System.out.println("关闭游戏");
            //直接关闭虚拟机
            System.exit(0);
        } else if (obj == accountItem) {
            System.out.println("个人微信");
            //创建一个弹窗对象
            JDialog jDialog = new JDialog();
            jDialog.setTitle("个人微信");
            //创建一个管理图片的容器对象JLabel
            JLabel jLabel = new JLabel(new ImageIcon("..\\puzzlegame\\image\\about.png"));
            jLabel.setBounds(0, 0, 263, 258);
            //把图片添加到弹框中
            jDialog.getContentPane().add(jLabel);
            //给弹框设置大小
            jDialog.setSize(344, 344);
            //给弹框置顶
            jDialog.setAlwaysOnTop(true);
            //给弹框设置居中
            jDialog.setLocationRelativeTo(null);
            //弹框不关闭,则无法操作下方界面
            jDialog.setModal(true);
            //让弹框显示出来
            jDialog.setVisible(true);
        } else if (obj == girl || obj == animal || obj == sport) {
            int number = r.nextInt(getImageNumber(obj)) + 1;
            path = getPath(obj) + number + "\\";
            //计步器清零
            step = 0;
            //再次打乱二维数组中的数据
            initData();
            //重新加载图片
            initImage();
        }
    }


    //获取图片路径
    public String getPath(Object obj) {
        if (obj == girl) {
            path = "..\\puzzlegame\\image\\girl\\girl";
        } else if (obj == sport) {
            path = "..\\puzzlegame\\image\\sport\\sport";
        } else if (obj == animal) {
            path = "..\\puzzlegame\\image\\animal\\animal";
        }
        return path;
    }

    //获取图片数目,用于随机数范围
    public int getImageNumber(Object obj) {
        if (obj == girl) {
            imageNum = 13;
        } else if (obj == sport) {
            imageNum = 10;
        } else if (obj == animal) {
            imageNum = 8;
        }
        return imageNum;
    }
}
