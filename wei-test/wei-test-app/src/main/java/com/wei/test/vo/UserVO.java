package com.wei.test.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 统一返回值
 *
 * @author : weierming
 * @date : 2020/7/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {

    private static final long serialVersionUID = 6488177830405564354L;

    private Long userId;

    private String userName;

    private Date date;

    private List<UserVO> list;

    private UserVO userVO;
}