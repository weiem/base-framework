package com.wei.base.springframework.util.kaptcha;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 运算符验证码字符生成器
 *
 * @author : weierming
 * @date : 2020/10/12
 */
public class OperatorTextCreator extends DefaultTextCreator {

    // 运算表达式及结果值
    StringBuilder result = new StringBuilder();

    /**
     * 获取运算验证码不超过两位数
     *
     * @return 返回运算验证码
     */
    @Override
    public String getText() {
        Random random = new Random(System.currentTimeMillis());
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int operationalRules = random.nextInt(4);

        switch (operationalRules) {
            case 0:
                add(x, y);
                break;
            case 1:
                subtract(x, y);
                break;
            case 2:
                multiply(x, y);
                break;
            // 除法计算复杂去掉
//            case 3 : divide(x, y); break;
            default:
                multiply(x, y);
                break;
        }

        return result.toString();
    }

    /**
     * 加法运算
     *
     * @param x 变量x
     * @param y 变量y
     */
    private void add(int x, int y) {
        result.append(x);
        result.append(" + ");
        result.append(y);
        result.append(" = ?@");
        result.append(x + y);
    }

    /**
     * 减法运算
     *
     * @param x 变量x
     * @param y 变量y
     */
    private void subtract(int x, int y) {
        int max = Math.max(x, y);
        int min = Math.min(x, y);
        result.append(max);
        result.append(" - ");
        result.append(min);
        result.append(" = ?@");
        result.append(max - min);
    }

    /**
     * 乘法运算
     *
     * @param x 变量x
     * @param y 变量y
     */
    private void multiply(int x, int y) {
        int value = x * y;
        result.append(x);
        result.append(value > 100 ? " + " : " * ");
        result.append(y);
        result.append(" = ?@");
        result.append(value > 100 ? x + y : x * y);
    }

    /**
     * 除法运算
     *
     * @param x 变量x
     * @param y 变量y
     */
    private void divide(int x, int y) {
        int max = Math.max(x, y);
        int min = Math.min(x, y);
        if (min == 0) {
            multiply(max, min);
        } else if (max % min == 0) {
            result.append(max);
            result.append(" / ");
            result.append(min);
            result.append(" = ?@");
            result.append(max / min);
        } else {
            result.append(max);
            result.append(" % ");
            result.append(min);
            result.append(" = ?@");
            result.append(max % min);
        }
    }

}