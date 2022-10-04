package com.example.ecommerce.common;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.List;
public class Utils {
    public static <T> List<T> convertJsonStringToListObject(String jsonString, Class<T[]> objectclass)
            throws Exception {
        jsonString = Strings.isNullOrEmpty(jsonString) ? "[]" : jsonString;
        ObjectMapper mapper = new ObjectMapper();
        List<T> result = Arrays.asList(mapper.readValue(jsonString, objectclass));
        return result;
    }


//    public static String convertListObjectToJsonArray(List<?> objects) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            final ByteArrayOutputStream out = new ByteArrayOutputStream();
//            mapper.writeValue(out, objects);
//            final byte[] data = out.toByteArray();
//            return new String(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}