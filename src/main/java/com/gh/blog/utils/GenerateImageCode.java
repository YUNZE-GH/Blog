package com.gh.blog.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @author gaohan
 * @version 1.0
 * @date 2020/8/27 17:19
 */
public class GenerateImageCode {

    public static String drawRandowmText(int width, int height, BufferedImage verifyImg) {
        Graphics2D graphics2D = (Graphics2D) verifyImg.getGraphics();
        graphics2D.setColor(Color.white);
        graphics2D.fillRect(0, 0, width, height);
        graphics2D.setFont(new Font("微软雅黑", Font.BOLD, 25));

        String baseNumLetter = "123456789ABCDEFGHJKLMNPQRSTUVWXYZ"; // abcdefghijklmnopqrstuvwxyz

        StringBuffer stringBuffer = new StringBuffer();
        int x = 10;  // 旋转原点的 x 坐标
        String ch = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            graphics2D.setColor(getRandomColor());
            // 设置字体旋转角度
            int degree = random.nextInt() % 30;  // 角度小于30度
            int dot = random.nextInt(baseNumLetter.length());
            ch = baseNumLetter.charAt(dot) + "";
            stringBuffer.append(ch);
            // 正向旋转
            graphics2D.rotate(degree * Math.PI / 180, x, 25);
            // 定义验证码在图片上的坐标
            graphics2D.drawString(ch, x, 25);
            // 反向旋转
            graphics2D.rotate(-degree * Math.PI / 180, x, 25);
            x += 20;
        }

        // 画干扰线
        for (int i = 0; i < 6; i++) {
            // 设置随机颜色
            graphics2D.setColor(getRandomColor());
            // 随机画线
            graphics2D.drawLine(random.nextInt(width), random.nextInt(height),
                    random.nextInt(width), random.nextInt(height));
        }
        // 添加噪点
        for (int i = 0; i < 30; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics2D.setColor(getRandomColor());
            graphics2D.fillRect(x1, y1, 2, 2);
        }

        return stringBuffer.toString();
    }

    // 随机颜色
    private static Color getRandomColor() {
        Random ran = new Random();
        Color color = new Color(ran.nextInt(256),
                ran.nextInt(256), ran.nextInt(256));
        return color;
    }
}
