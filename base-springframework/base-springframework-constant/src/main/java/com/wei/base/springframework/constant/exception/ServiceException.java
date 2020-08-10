package com.wei.base.springframework.constant.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3566267379569477434L;

    private Integer code;

    private String message;

    public ServiceException(BaseException baseException) {
        code = baseException.getCode();
        message = baseException.getMsg();
    }

    public ServiceException(BaseException baseException, String... msgs) {
        code = baseException.getCode();

        StringBuilder stringBuilder = new StringBuilder();
        if (msgs != null && msgs.length > 0) {
            for (String msg : msgs) {
                stringBuilder.append(msg + ",");
            }
        }

        stringBuilder.setLength(stringBuilder.length() - 1);
        message = String.format(baseException.getMsg(), stringBuilder.toString());
    }

    public ServiceException(BaseException baseException, Long... msgs) {
        code = baseException.getCode();

        StringBuilder stringBuilder = new StringBuilder();
        if (msgs != null && msgs.length > 0) {
            for (Long msg : msgs) {
                stringBuilder.append(msg + ",");
            }
        }

        stringBuilder.setLength(stringBuilder.length() - 1);
        message = String.format(baseException.getMsg(), stringBuilder.toString());
    }
}
