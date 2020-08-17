package org.zhx.common.commonnetwork.retrofit;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.ParserConfig;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final Feature[] EMPTY_SERIALIZER_FEATURES = new Feature[0];

    private Type mType;

    private ParserConfig config;
    private int featureValues;
    private Feature[] features;

    FastJsonResponseBodyConverter(Type type, ParserConfig config, int featureValues,
                                  Feature... features) {
        mType = type;
        this.config = config;
        this.featureValues = featureValues;
        this.features = features;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            String str = new String(value.bytes());
            if (!TextUtils.isEmpty(str)) {
                if (!str.startsWith("{") && !str.startsWith("[")) {
                    str = JSONObject.toJSONString(str);
                }
            }
            return JSONObject.parseObject(str, mType, config, featureValues,
                    features != null ? features : EMPTY_SERIALIZER_FEATURES);
        } finally {
            value.close();
        }
    }
}
