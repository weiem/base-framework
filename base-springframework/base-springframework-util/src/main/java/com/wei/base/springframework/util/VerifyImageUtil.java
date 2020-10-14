package com.wei.base.springframework.util;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wei.base.springframework.constant.exception.ServiceException;
import com.wei.base.springframework.util.enums.UtilExceptionEnum;
import com.wei.base.springframework.util.kaptcha.vo.CharVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.OperatorVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.SlideVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.TextSelectionVerifyImage;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Base64Utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * 验证码生成工具类
 *
 * @author : weierming
 * @date : 2020/10/13
 */
public class VerifyImageUtil {

    /**
     * 滑动图片文件夹路径
     */
    private static final String TARGETS_PATH = "static/targets";

    private static final String TEMPLATES_PATH = "static/templates";

    private static final String FORMAT_NAME = "png";

    //生成汉字的个数
    private static Integer[] CHARACTERS_NUMBER = new Integer[]{1, 2, 3, 4, 5};

    //汉字颜色随机范围
    private static Color[] COLORS = {Color.YELLOW, Color.PINK, Color.RED};

    public static final String UNDER_LINE = "_";

    private static String HAN = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";

    /**
     * 获取字符验证码
     *
     * @return 字符验证码
     */
    public static CharVerifyImage getCharVerifyImage() throws IOException {
        DefaultKaptcha defaultKaptcha = getCharKaptcha();
        String text = defaultKaptcha.createText();
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);

        CharVerifyImage charVerifyImage = new CharVerifyImage();
        charVerifyImage.setValue(text);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            if (!ImageIO.write(bufferedImage, FORMAT_NAME, byteArrayOutputStream)) {
                throw new ServiceException(UtilExceptionEnum.GENERATE_VERIFY_IMAGE_ERROR);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();
            //  图片base64加密
            charVerifyImage.setImage(Base64Utils.encodeToString(bytes));
        }

        return charVerifyImage;
    }

    /**
     * 获取运算符验证码
     *
     * @return 字符验证码
     */
    public static OperatorVerifyImage getOperatorVerifyImage() throws IOException {
        DefaultKaptcha defaultKaptcha = getOperatorKaptcha();
        String text = defaultKaptcha.createText();
        List<String> operators = StringUtil.splitList(text, "@");
        OperatorVerifyImage operatorVerifyImage = new OperatorVerifyImage();
        if (CollectionUtils.isEmpty(operators)) {
            throw new ServiceException(UtilExceptionEnum.GENERATE_VERIFY_IMAGE_ERROR);
        }

        BufferedImage bufferedImage = defaultKaptcha.createImage(operators.get(0));
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byteArrayOutputStream.close();
            if (!ImageIO.write(bufferedImage, FORMAT_NAME, byteArrayOutputStream)) {
                throw new ServiceException(UtilExceptionEnum.GENERATE_VERIFY_IMAGE_ERROR);
            }

            byte[] bytes = byteArrayOutputStream.toByteArray();

            // 图片base64加密
            operatorVerifyImage.setValue(operators.get(1));
            operatorVerifyImage.setImage(Base64Utils.encodeToString(bytes));
            return operatorVerifyImage;
        }
    }

    /**
     * 滑动图片验证码
     *
     * @return 滑动图片实体
     * @throws IOException io异常
     */
    public static SlideVerifyImage getSlideVerifyImage() throws IOException {
        Random random = new Random(System.currentTimeMillis());
        // 随机取得原图文件夹中一张图片
        File originImageFile = getRandomFile(TARGETS_PATH);

        // 获取模板图片文件
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:" + TEMPLATES_PATH + "/template.png");
        File templateImageFile = resource.getFile();

        // 获取描边图片文件
        resource = resourceLoader.getResource("classpath:" + TEMPLATES_PATH + "/border.png");
        File borderImageFile = resource.getFile();
        // 获取描边图片类型
        String borderImageFileType = borderImageFile.getName().substring(borderImageFile.getName().lastIndexOf(".") + 1);

        // 获取原图文件类型
        String originImageFileType = originImageFile.getName().substring(originImageFile.getName().lastIndexOf(".") + 1);
        // 获取模板图文件类型
        String templateImageFileType = templateImageFile.getName().substring(templateImageFile.getName().lastIndexOf(".") + 1);

        // 读取原图
        BufferedImage verificationImage = ImageIO.read(originImageFile);
        // 读取模板图
        BufferedImage readTemplateImage = ImageIO.read(templateImageFile);

        // 读取描边图片
        BufferedImage borderImage = ImageIO.read(borderImageFile);

        // 获取原图感兴趣区域坐标
        Integer[] coordinate = generateCutoutCoordinates(verificationImage, readTemplateImage);

        // 根据原图生成遮罩图和切块图
        SlideVerifyImage slideVerifyImage = pictureTemplateCutout(originImageFile, originImageFileType, templateImageFile, templateImageFileType, coordinate);

        //   剪切图描边
        cutoutImageEdge(slideVerifyImage, borderImage, borderImageFileType);
        return slideVerifyImage;
    }

    /**
     * 生成文字点选验证码
     *
     * @return 点选文字对象
     * @throws IOException io异常
     */
    public static TextSelectionVerifyImage getTextSelectionVerifyImage() throws IOException {
        File originImageFile = getRandomFile(TARGETS_PATH);

        //生成背景图片
        BufferedImage image = ImageIO.read(originImageFile);
        Graphics graphics = image.getGraphics();
        Font font = new Font("宋体", Font.BOLD, 30);

        StringBuilder sb = new StringBuilder();

        //转成集合
        List<Integer> intList = Lists.newArrayList(CHARACTERS_NUMBER);

        //重新随机排序
        Collections.shuffle(intList);

        //list参数坐标参数 用于校验是否验证通过
        List<String> codeAxis = Lists.newArrayList();

        int x = 0;
        int y = 0;
        Random random = new Random(System.currentTimeMillis());
        //定义随机1到arr.length某一个字不参与校验
        int num = random.nextInt(CHARACTERS_NUMBER.length) + 1;
        Set<String> currentCh = Sets.newHashSet();

        for (int i = 0; i < CHARACTERS_NUMBER.length; i++) {
            // 5个汉字,只点4个
            String ch = getRandomHan(currentCh);
            int place = intList.get(i);
            int[] axis = getAxis(place);
            x = axis[0];
            y = axis[1];

            //字体颜色
            graphics.setColor(COLORS[random.nextInt(COLORS.length)]);
            //设置角度
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(new Random().nextInt(50) + 50), 0, 0);
            Font rotatedFont = font.deriveFont(affineTransform);
            graphics.setFont(rotatedFont);
            graphics.drawString(ch, x, y);

            if (place != num) {
                sb.append(ch);
                codeAxis.add(x + UNDER_LINE + y);
            }
        }

        // 创建顶部图片
        TextSelectionVerifyImage textSelectionVerifyImage = new TextSelectionVerifyImage();
        BufferedImage bi = generateTopPicture(image, sb, textSelectionVerifyImage);
        BufferedImage combined = new BufferedImage(image.getWidth(), image.getHeight() + bi.getHeight(), BufferedImage.TYPE_INT_RGB);

        //合并
        Graphics g = combined.getGraphics();
        g.drawImage(bi, 0, 0, null);
        g.drawImage(image, 0, bi.getHeight(), null);

        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            textSelectionVerifyImage.setImage(getBaseImageString(combined, FORMAT_NAME, byteArrayOutputStream));
        }

        textSelectionVerifyImage.setCodeAxis(codeAxis);
        return textSelectionVerifyImage;
    }

    /**
     * 获取随机中文
     *
     * @return 生成的中文
     */
    private static String getRandomHan(Set<String> currentCh) {
        String ch;
        do {
            ch = HAN.charAt(new Random().nextInt(HAN.length())) + "";
            currentCh.add(ch);
        } while (!currentCh.contains(ch));
        return ch;
    }

    /**
     * 生成坐标轴
     *
     * @param place
     * @return
     */
    private static int[] getAxis(int place) {
        int x = 0, y = 0;
        switch (place) {
            case 1:
                x = new Random().nextInt(30) + 40; // 自己定义的位子坐标
                y = new Random().nextInt(30) + 40; // i=1的时候，y的值
                break;
            case 2:
                x = new Random().nextInt(40) + 120; // 自己定义的位子坐标
                y = new Random().nextInt(30) + 50; // i=2的时候，y的值
                break;
            case 3:
                x = new Random().nextInt(70) + 200; // 自己定义的位子坐标
                y = new Random().nextInt(50) + 100; // i=3的时候，y的值
                break;
            case 4:
                x = new Random().nextInt(70) + 80; // i=4的时候，x的值
                y = new Random().nextInt(30) + 90; // 自己定义的位子坐标
                break;
            case 5:
                x = new Random().nextInt(70) + 180; // i=4的时候，x的值
                y = new Random().nextInt(30) + 50; // 自己定义的位子坐标
                break;

            default:
                break;
        }

        return new int[]{x, y};
    }

    /**
     * 生成顶部图片
     *
     * @param image
     * @param sb
     * @return
     */
    private static BufferedImage generateTopPicture(BufferedImage image, StringBuilder sb, TextSelectionVerifyImage textSelectionVerifyImage) {
        BufferedImage bi = new BufferedImage(image.getWidth(), 25, BufferedImage.TYPE_INT_RGB);
        Graphics gra = bi.getGraphics();
        // 设置背景颜色
        gra.setColor(Color.WHITE);
        // 填充区域
        gra.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        // 设置文字背景颜色
        String tips = "请依次点击:" + sb;
        Font fontTips = new Font("Microsoft YaHei", Font.BOLD, 16);
        gra.setFont(fontTips);
        gra.setColor(Color.DARK_GRAY);
        gra.drawString(tips, 10, bi.getHeight() / 2 + fontTips.getSize() / 2);
        textSelectionVerifyImage.setTips(tips);

        return bi;
    }

    /**
     * 切块图描边
     *
     * @param slideVerifyImage    图片容器
     * @param borderImage         描边图
     * @param borderImageFileType 描边图类型
     * @throws IOException io异常
     */
    private static void cutoutImageEdge(SlideVerifyImage slideVerifyImage, BufferedImage borderImage, String borderImageFileType) throws IOException {
        String cutoutImageString = slideVerifyImage.getCutoutImage();
        //  图片解密成二进制字符创
        byte[] bytes = Base64Utils.decodeFromString(cutoutImageString);
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            //  读取图片
            BufferedImage cutoutImage = ImageIO.read(byteArrayInputStream);
            //  获取模板边框矩阵， 并进行颜色处理
            int[][] borderImageMatrix = getMatrix(borderImage);
            for (int i = 0; i < borderImageMatrix.length; i++) {
                for (int j = 0; j < borderImageMatrix[0].length; j++) {
                    int rgb = borderImage.getRGB(i, j);
                    if (rgb < 0) {
                        cutoutImage.setRGB(i, j, -7237488);
                    }
                }
            }

            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                ImageIO.write(cutoutImage, borderImageFileType, byteArrayOutputStream);
                //  新模板图描边处理后转成二进制字符串
                byte[] cutoutImageBytes = byteArrayOutputStream.toByteArray();
                slideVerifyImage.setCutoutImage(Base64Utils.encodeToString(cutoutImageBytes));
            }
        }
    }

    /**
     * 随机获取指定文件夹下的一个文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    private static File getRandomFile(String path) throws IOException {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:" + path);
        File verifyImageImport = resource.getFile();
        File[] verifyImages = verifyImageImport.listFiles();
        if (verifyImages == null || verifyImages.length <= 0) {
            throw new ServiceException(UtilExceptionEnum.NO_VERIFY_IMAGE);
        }

        Random random = new Random(System.currentTimeMillis());
        // 随机取得原图文件夹中一张图片
        return verifyImages[random.nextInt(verifyImages.length)];
    }


    /**
     * 生成感兴趣区域坐标
     *
     * @param verificationImage 源图
     * @param templateImage     模板图
     * @return 裁剪坐标 [0] x轴, [1] y轴
     */
    private static Integer[] generateCutoutCoordinates(BufferedImage verificationImage, BufferedImage templateImage) {
        int x, y;
        //  原图宽度
        int VERIFICATION_IMAGE_WIDTH = verificationImage.getWidth();
        //  原图高度
        int VERIFICATION_IMAGE_HEIGHT = verificationImage.getHeight();
        //  抠图模板宽度
        int templateImageWidth = templateImage.getWidth();
        //  抠图模板高度
        int templateImageHeight = templateImage.getHeight();

        Random random = new Random(System.currentTimeMillis());

        //  取范围内坐标数据，坐标抠图一定要落在原图中，否则会导致程序错误
        x = random.nextInt(VERIFICATION_IMAGE_WIDTH - templateImageWidth) % (VERIFICATION_IMAGE_WIDTH - templateImageWidth - templateImageWidth + 1) + templateImageWidth;
        if (templateImageHeight - VERIFICATION_IMAGE_HEIGHT >= 0) {
            y = random.nextInt(10);
        } else {
            y = random.nextInt(VERIFICATION_IMAGE_HEIGHT - templateImageWidth) % (VERIFICATION_IMAGE_HEIGHT - templateImageWidth - templateImageWidth + 1) + templateImageWidth;
        }

        return new Integer[]{x, y};
    }

    /**
     * 根据模板图裁剪图片，生成源图遮罩图和裁剪图
     *
     * @param originImageFile       源图文件
     * @param originImageFileType   源图文件扩展名
     * @param templateImageFile     模板图文件
     * @param templateImageFileType 模板图文件扩展名
     * @param coordinate            感兴趣区域
     * @return 滑动图片对象
     */
    private static SlideVerifyImage pictureTemplateCutout(File originImageFile, String originImageFileType, File templateImageFile, String templateImageFileType, Integer[] coordinate) throws IOException {
        // 读取模板图
        BufferedImage templateImage = ImageIO.read(templateImageFile);

        // 读取原图
        BufferedImage originImage = ImageIO.read(originImageFile);
        int templateImageWidth = templateImage.getWidth();
        int templateImageHeight = templateImage.getHeight();
        Integer x = coordinate[0];
        Integer y = coordinate[1];

        // 切块图 根据模板图尺寸创建一张透明图片
        BufferedImage cutoutImage = new BufferedImage(templateImageWidth, templateImageHeight, templateImage.getType());

        // 根据坐标获取感兴趣区域
        BufferedImage interestArea = getInterestArea(x, y, templateImageWidth, templateImageHeight, originImageFile, originImageFileType);
        // 根据模板图片切图
        cutoutImageByTemplateImage(interestArea, templateImage, cutoutImage);

        // 图片绘图
        int bold = 5;
        Graphics2D graphics = cutoutImage.createGraphics();
        graphics.setBackground(Color.white);

        // 设置抗锯齿属性
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setStroke(new BasicStroke(bold, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
        graphics.drawImage(cutoutImage, 0, 0, null);
        graphics.dispose();

        // 原图生成遮罩
        BufferedImage shadeImage = generateShadeByTemplateImage(originImage, templateImage, x, y);

        SlideVerifyImage slideVerifyImage = new SlideVerifyImage();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            // 图片加密成base64字符串
            slideVerifyImage.setImage(getBaseImageString(shadeImage, templateImageFileType, byteArrayOutputStream));
            slideVerifyImage.setCutoutImage(getBaseImageString(cutoutImage, templateImageFileType, byteArrayOutputStream));
        }

        slideVerifyImage.setX(x);
        slideVerifyImage.setY(y);
        return slideVerifyImage;
    }

    /**
     * 获取图片字符串
     *
     * @param image                 图片对象
     * @param formatName            图片格式
     * @param byteArrayOutputStream 输出流
     * @return 返回图片base加密字符串
     * @throws IOException io错误
     */
    private static String getBaseImageString(BufferedImage image, String formatName, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        ImageIO.write(image, formatName, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.flush();
        byteArrayOutputStream.reset();
        return Base64Utils.encodeToString(imageBytes);
    }

    /**
     * 获取感兴趣区域
     *
     * @param x                   感兴趣区域X轴
     * @param y                   感兴趣区域Y轴
     * @param templateImageWidth  模板图宽度
     * @param templateImageHeight 模板图高度
     * @param originImage         源图
     * @param originImageType     源图扩展名
     * @return 感兴趣图片区域
     */
    private static BufferedImage getInterestArea(int x, int y, int templateImageWidth, int templateImageHeight, File originImage, String originImageType) throws IOException {
        Iterator<ImageReader> imageReaderIterator = ImageIO.getImageReadersByFormatName(originImageType);
        ImageReader imageReader = imageReaderIterator.next();
        //  获取图片流
        try (ImageInputStream imageInputStream = ImageIO.createImageInputStream(originImage)) {
            //  图片输入流顺序读写
            imageReader.setInput(imageInputStream, true);
            ImageReadParam imageReadParam = imageReader.getDefaultReadParam();

            //  根据坐标生成矩形
            Rectangle rectangle = new Rectangle(x, y, templateImageWidth, templateImageHeight);
            imageReadParam.setSourceRegion(rectangle);
            return imageReader.read(0, imageReadParam);
        }
    }

    /**
     * 根据模板图抠图
     *
     * @param interestArea  感兴趣区域图
     * @param templateImage 模板图
     * @param cutoutImage   裁剪图
     */
    private static void cutoutImageByTemplateImage(BufferedImage interestArea, BufferedImage templateImage, BufferedImage cutoutImage) {
        //  获取模板图片矩阵
        int[][] templateImageMatrix = getMatrix(templateImage);

        //  将模板图非透明像素设置到剪切图中
        for (int i = 0; i < templateImageMatrix.length; i++) {
            for (int j = 0; j < templateImageMatrix[0].length; j++) {
                int rgb = templateImageMatrix[i][j];
                if (rgb < 0) {
                    cutoutImage.setRGB(i, j, interestArea.getRGB(i, j));
                }
            }
        }
    }

    /**
     * 图片生成图像矩阵
     *
     * @param bufferedImage 图片源
     * @return 图片矩阵
     */
    private static int[][] getMatrix(BufferedImage bufferedImage) {
        int[][] matrix = new int[bufferedImage.getWidth()][bufferedImage.getHeight()];
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                matrix[i][j] = bufferedImage.getRGB(i, j);
            }
        }

        return matrix;
    }

    /**
     * 根据模板图生成遮罩图
     *
     * @param originImage   源图
     * @param templateImage 模板图
     * @param x             感兴趣区域X轴
     * @param y             感兴趣区域Y轴
     * @return 遮罩图
     */
    private static BufferedImage generateShadeByTemplateImage(BufferedImage originImage, BufferedImage templateImage, int x, int y) {
        //  根据原图，创建支持alpha通道的rgb图片
        BufferedImage shadeImage = new BufferedImage(originImage.getWidth(), originImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        //  原图片矩阵
        int[][] originImageMatrix = getMatrix(originImage);
        //  模板图片矩阵
        int[][] templateImageMatrix = getMatrix(templateImage);

        //  将原图的像素拷贝到遮罩图
        for (int i = 0; i < originImageMatrix.length; i++) {
            for (int j = 0; j < originImageMatrix[0].length; j++) {
                int rgb = originImage.getRGB(i, j);
                //  获取rgb色度
                int r = (0xff & rgb);
                int g = (0xff & (rgb >> 8));
                int b = (0xff & (rgb >> 16));
                //  无透明处理
                rgb = r + (g << 8) + (b << 16) + (255 << 24);
                shadeImage.setRGB(i, j, rgb);
            }
        }

        //  对遮罩图根据模板像素进行处理
        for (int i = 0; i < templateImageMatrix.length; i++) {
            for (int j = 0; j < templateImageMatrix[0].length; j++) {
                int rgb = templateImage.getRGB(i, j);
                //对源文件备份图像(x+i,y+j)坐标点进行透明处理
                if (rgb < 0) {
                    //  对遮罩透明处理
//                    int originRGB = shadeImage.getRGB(x + i, y + j);
//                    int r = (0xff & originRGB);
//                    int g = (0xff & (originRGB >> 8));
//                    int b = (0xff & (originRGB >> 16));
//                    originRGB = r + (g << 8) + (b << 16) + (140 << 24);
//                    shadeImage.setRGB(x + i, y + j, originRGB);

                    // 设置遮罩为白颜色
                    shadeImage.setRGB(x + i, y + j, Color.WHITE.getRGB());
                }
            }
        }

        return shadeImage;
    }

    /**
     * 获取字符验证器
     *
     * @return 返回字符验证器
     */
    private static DefaultKaptcha getCharKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 图片边框
        properties.setProperty("kaptcha.border", "yes");
        // 边框颜色
        properties.setProperty("kaptcha.border.color", "105,179,90");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");
        // 图片宽
        properties.setProperty("kaptcha.image.width", "110");
        // 图片高
        properties.setProperty("kaptcha.image.height", "40");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "30");
        // session key
        properties.setProperty("kaptcha.session.key", "kaptchaCharCode");
        // 验证码文本字符间距 默认为2
        properties.setProperty("kaptcha.textproducer.char.space", "3");
        // 验证码长度
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
//        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.textproducer.font.names", "微软雅黑");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }


    /**
     * 获取运算验证码器
     *
     * @return 返回运算验证码器
     */
    private static DefaultKaptcha getOperatorKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 是否有边框 默认为true 我们可以自己设置yes，no
        properties.setProperty("kaptcha.border", "no");
        // 边框颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.border.color", "55,160,204");
        // 验证码文本字符颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        // 背景渐变色，开始颜色
        properties.setProperty("kaptcha.background.clear.from", "234,172,236");
        // 背景渐变色，结束颜色
        properties.setProperty("kaptcha.background.clear.to", "234,144,115");
        // 验证码图片宽度 默认为200
        properties.setProperty("kaptcha.image.width", "170");
        // 验证码图片高度 默认为50
        properties.setProperty("kaptcha.image.height", "60");
        // 验证码文本字符大小 默认为40
        properties.setProperty("kaptcha.textproducer.font.size", "35");
        // KAPTCHA_SESSION_KEY
        properties.setProperty("kaptcha.session.key", "kaptchaMathCode");
// --------------验证码文本生成器,这里需要设置成自己项目的包名----------------------
        properties.setProperty("kaptcha.textproducer.impl", "com.wei.base.springframework.util.kaptcha.OperatorTextCreator");
        // 验证码文本字符间距 默认为2
        properties.setProperty("kaptcha.textproducer.char.space", "3");
        // 验证码文本字符长度 默认为9
        properties.setProperty("kaptcha.textproducer.char.length", "9");
        // 验证码文本字体样式 默认为new Font("Arial", 1, fontSize), new Font("Courier", 1, fontSize)
        properties.setProperty("kaptcha.textproducer.font.names", "Arial,Courier");
        // 验证码噪点颜色 默认为Color.BLACK
        properties.setProperty("kaptcha.noise.color", "243,79,67");
        // 干扰实现类
//        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        // 图片样式 水纹com.google.code.kaptcha.impl.WaterRipple 鱼眼com.google.code.kaptcha.impl.FishEyeGimpy 阴影com.google.code.kaptcha.impl.ShadowGimpy
//        properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}