package com.wei.base.springframework.core.http.converter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wei.base.springframework.util.StringUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 请求参数处理器
 *
 * @author : weierming
 * @date : 2020/7/14
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {

    private Map<String, String[]> params = Maps.newHashMap();

    private static final String CHARSET_NAME = Charsets.UTF_8.name();

    public ParameterRequestWrapper(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        // 将参数表，赋予给当前的Map以便于持有request中的参数
        Map<String, String[]> requestMap = request.getParameterMap();
        params.putAll(requestMap);
        //
        modifyParameterValues();
    }

    /**
     * 重写getInputStream方法 post类型的请求参数必须通过流才能获取到值
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 非json类型，直接返回
        try {
            if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
                return super.getInputStream();
            }

            // 为空，直接返回
            String json = IOUtils.toString(super.getInputStream(), CHARSET_NAME);
            if (StringUtil.isEmpty(json)) {
                return super.getInputStream();
            }

            ByteArrayInputStream bis = null;
            try {
                Map<String, Object> map = JSON.parseObject(json);
                mapTrim(map);

                bis = new ByteArrayInputStream(JSON.toJSONString(map).getBytes(CHARSET_NAME));
            } catch (Exception e) {
                JSONArray jsonArray = JSON.parseArray(json);
                if (jsonArray.isEmpty()) {
                    bis = new ByteArrayInputStream(JSON.toJSONString(jsonArray).getBytes(CHARSET_NAME));
                    return new MyServletInputStream(bis);
                }

                List<Map<String, Object>> list = Lists.newArrayListWithCapacity(jsonArray.size());
                jsonArray.stream().forEach(vo -> {
                    Map<String, Object> map = (Map<String, Object>) vo;
                    mapTrim(map);

                    list.add(map);
                });

                bis = new ByteArrayInputStream(JSON.toJSONString(list).getBytes(CHARSET_NAME));
            }

            return new MyServletInputStream(bis);
        } catch (Exception e) {
            return super.getInputStream();
        }
    }

    /**
     * map value去空格
     *
     * @param map
     */
    private void mapTrim(Map<String, Object> map) {
        if (MapUtils.isEmpty(map)) {
            return;
        }

        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();

        while (it.hasNext()) {
            String key = it.next();
            Object values = map.get(key);
            if (values instanceof String) {
                values = StringUtil.trim((String) values);
            } else if (values instanceof JSONArray) {
                // 如果是数组且不为空则循环将value中的空格去除
                JSONArray jsonArray = (JSONArray) values;
                if (!jsonArray.isEmpty()) {
                    List<Map<String, Object>> list = Lists.newArrayListWithExpectedSize(jsonArray.size());
                    jsonArray.stream().forEach(json -> {
                        Map<String, Object> jsonMap = (Map<String, Object>) json;
                        mapTrim(jsonMap);

                        list.add(jsonMap);
                    });

                    values = list;
                }
            } else if (values instanceof JSONObject) {
                Map<String, Object> jsonMap = (Map<String, Object>) values;
                mapTrim(jsonMap);
                values = jsonMap;
            }

            map.put(key, values);
        }
    }

    /**
     * 将parameter的值去除空格后重写回去
     */
    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {// 同上
        return params.get(name);
    }

    class MyServletInputStream extends ServletInputStream {
        private ByteArrayInputStream bis;

        public MyServletInputStream(ByteArrayInputStream bis) {
            this.bis = bis;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }
    }
}