package com.ctrl.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

/**
 * The type Captcha util.验证码工具类
 *
 * @author dalaodi
 */
public class CaptchaUtil {

    /**
     * 验证码字符集合，排除了易混淆的字符（0、O、1、I）
     */

    private static final String CODE_CHARS = "23456789abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";

    /*
      验证码长度
     */

    /**
     * 验证码图片的宽度和高度
     */
    private static final int IMAGE_WIDTH = 80;

    private static final int IMAGE_HEIGHT = 28;

    /**
     * 字体名称和大小
     */
    private static final String FONT_NAME = "Arial";
    private static final int FONT_SIZE = 18;

    /**
     * Generate captcha.
     *
     * @param codeLength the code length
     * @return the captcha
     */
    public static Captcha generate(int codeLength) {
        Random random = new Random();
        StringBuilder codeBuilder = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            char codeChar = CODE_CHARS.charAt(random.nextInt(CODE_CHARS.length()));
            codeBuilder.append(codeChar);
        }
        return drawImage(codeBuilder.toString());
    }

    /**
     * Draw image buffered image.
     *
     * @param code the code
     * @return the buffered image
     */
    public static Captcha drawImage(String code) {
        String[] split = code.split("");
        BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Random random = new Random();
        graphics.setColor(new Color(240, 240, 240));
        graphics.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        for (int i = 0; i < split.length; i++) {
            // 绘制验证码字符
            graphics.setFont(new Font(FONT_NAME, Font.BOLD, FONT_SIZE));
            graphics.setColor(new Color(random.nextInt(50), random.nextInt(150), random.nextInt(200)));
            graphics.drawString(String.valueOf(split[i]), i * 20 + 5, 20 + random.nextInt(8));
        }
        graphics.dispose();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        BASE64Encoder encoder = new BASE64Encoder();
        String pngBase64 = encoder.encodeBuffer(bytes).trim();
        pngBase64 = pngBase64.replaceAll("\n", "").replaceAll("\r", "");
        return new Captcha(code, "data:image/jpg;base64," + pngBase64);
    }

    /**
     * The type Captcha.
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Captcha implements Serializable {
        private String code;
        private String imageCode;
    }
}
