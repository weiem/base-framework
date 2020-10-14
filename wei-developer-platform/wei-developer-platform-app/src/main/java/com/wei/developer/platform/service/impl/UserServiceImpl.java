package com.wei.developer.platform.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.base.springframework.constant.exception.ServiceException;
import com.wei.base.springframework.util.StringUtil;
import com.wei.base.springframework.util.VerifyImageUtil;
import com.wei.base.springframework.util.kaptcha.enums.VerifyImageTypeEnum;
import com.wei.base.springframework.util.kaptcha.vo.CharVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.OperatorVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.SlideVerifyImage;
import com.wei.base.springframework.util.kaptcha.vo.TextSelectionVerifyImage;
import com.wei.developer.platform.constant.RedisConstant;
import com.wei.developer.platform.entity.User;
import com.wei.developer.platform.enums.DeveloperPlatformExceptionEnum;
import com.wei.developer.platform.mapper.UserMapper;
import com.wei.developer.platform.service.UserService;
import com.wei.developer.platform.vo.request.CheckVerifyImageRequest;
import com.wei.developer.platform.vo.respon.VerifyImageResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author wei
 * @since 2020-10-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 生成验证码图片
     *
     * @param verifyImageType 验证码类型
     * @return 验证码
     */
    @Override
    public VerifyImageResp generateVerifyImage(Integer verifyImageType) {
        VerifyImageTypeEnum enumByCode = VerifyImageTypeEnum.getEnumByCode(verifyImageType);
        if (enumByCode == null) {
            throw new ServiceException(DeveloperPlatformExceptionEnum.VERIFY_IMAGE_TYPE_ERROR);
        }

        Long id = IdWorker.getId();
        VerifyImageResp verifyImageResp = new VerifyImageResp();
        verifyImageResp.setId(id);
        try {
            switch (enumByCode) {
                case CHAR:
                    CharVerifyImage charVerifyImage = VerifyImageUtil.getCharVerifyImage();
                    verifyImageResp.setImage(charVerifyImage.getImage());
                    addVerifyImageCache(id, verifyImageType, charVerifyImage.getValue());
                    break;

                case OPERATION:
                    OperatorVerifyImage operatorVerifyImage = VerifyImageUtil.getOperatorVerifyImage();
                    verifyImageResp.setImage(operatorVerifyImage.getImage());
                    addVerifyImageCache(id, verifyImageType, operatorVerifyImage.getValue());
                    break;

                case SLIDE:
                    SlideVerifyImage slideVerifyImage = VerifyImageUtil.getSlideVerifyImage();
                    verifyImageResp.setImage(slideVerifyImage.getImage());
                    verifyImageResp.setCutoutImage(slideVerifyImage.getCutoutImage());
                    Integer y = slideVerifyImage.getY();
                    verifyImageResp.setY(slideVerifyImage.getY());
                    addVerifyImageCache(id, verifyImageType, slideVerifyImage.getX() + "-" + y);
                    break;

                case TEXT_SELECTION:
                    TextSelectionVerifyImage textSelectionVerifyImage = VerifyImageUtil.getTextSelectionVerifyImage();
                    verifyImageResp.setImage(textSelectionVerifyImage.getImage());
                    verifyImageResp.setTips(textSelectionVerifyImage.getTips());
                    addVerifyImageCache(id, verifyImageType, StringUtil.join(textSelectionVerifyImage.getCodeAxis(), ","));
                    break;

                default:
                    break;
            }
        } catch (Exception e) {
            throw new ServiceException(DeveloperPlatformExceptionEnum.GENERATE_VERIFY_IMAGE_ERROR);
        }

        return verifyImageResp;
    }

    /**
     * 添加验证码缓存
     *
     * @param id    id
     * @param type  验证码类型
     * @param value 验证码值
     */
    private void addVerifyImageCache(Long id, Integer type, String value) {
        redisTemplate.opsForValue().set(RedisConstant.VerifyImageRedisKey + type + ":" + id, value, 60, TimeUnit.SECONDS);
    }

    /**
     * 校验验证码
     *
     * @param request 验证码参数
     */
    @Override
    public void checkVerifyImage(CheckVerifyImageRequest request) {
        String redisKey = RedisConstant.VerifyImageRedisKey + request.getType() + ":" + request.getId();
        if (!redisTemplate.hasKey(redisKey)) {
            throw new ServiceException(DeveloperPlatformExceptionEnum.VERIFY_IS_INVALID);
        }

        if (!StringUtil.equals(redisTemplate.opsForValue().get(redisKey), request.getValue())) {
            throw new ServiceException(DeveloperPlatformExceptionEnum.VERIFY_ERROR);
        }
    }
}