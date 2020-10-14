package com.wei.base.springframework.core.http.converter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.wei.base.springframework.util.StringUtil;

import java.io.IOException;

/**
 * 序列化时将字符串前后空格过滤
 *
 * @author : weierming
 * @date : 2020/9/4
 */
public class StringWithoutSpaceDeserializer extends StdDeserializer<String> {

    private static final long serialVersionUID = -1291306222073325805L;

    public StringWithoutSpaceDeserializer(Class<String> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return StringUtil.trim(jsonParser.getText());
    }
}