package com.example.ecommerce.common;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.common.base.Strings;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    public static <T> List<T> convertJsonStringToListObject(String jsonString, Class<T[]> objectclass)
            throws Exception {
        jsonString = Strings.isNullOrEmpty(jsonString) ? "[]" : jsonString;
        ObjectMapper mapper = new ObjectMapper();
        List<T> result = Arrays.asList(mapper.readValue(jsonString, objectclass));
        return result;
    }

    public static String convertObjectToJson(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(object);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String convertObjectToJsonObject(Object object) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertListObjectToJsonArray(List<?> objects) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            mapper.writeValue(out, objects);
            final byte[] data = out.toByteArray();
            return new String(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T convertJsonStringToObject(String jsonString, Class<T> objectclass) throws Exception {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        T result = mapper.readValue(jsonString, objectclass);
        return result;
    }

    public static <T> String convertObjectToJsonString(Object data) throws Exception {

        if (data == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(data);
        return result;
    }

    public static String convertStringToDate(Date indate) {
        String dateString = null;
        SimpleDateFormat sdfr = new SimpleDateFormat("dd/MM/yyyy");

        try {
            dateString = sdfr.format(indate);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return dateString;
    }

    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_INPUT_FORMAT);
        if (dateString == null || Strings.isNullOrEmpty(dateString)) {
            return new Date();
        } else {
            return sdf.parse(dateString);
        }
    }

    public static Date convertStringToDateTimeDBFormat(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_INPUT_FORMAT);
        if (dateString == null || Strings.isNullOrEmpty(dateString)) {
            return new Date();
        } else {
            return sdf.parse(dateString);
        }
    }

    public static boolean createFolder(String path) {
        File theDir = new File(path);
        if (!theDir.exists()) {
            boolean result = false;

            try {
                System.out.println("Begin make dir: " + path);
                theDir.setWritable(true, false);
                theDir.mkdir();
                result = true;
            } catch (SecurityException se) {
                result = false;
                se.printStackTrace();
            }
            return result;
        } else {
            return true;
        }
    }
    public static String saveUploadedFile(MultipartFile image, String resourcePath, String filePath) {
        String imageUrl = "";
        if (!image.isEmpty()) {
            try {
                createFolder(resourcePath + filePath);
                byte[] bytes = image.getBytes();
                filePath = filePath + Calendar.getInstance().getTimeInMillis() + image.getOriginalFilename();
                String fPath = resourcePath + filePath;
                Path path = Paths.get(fPath);
                Files.write(path, bytes);
                imageUrl = filePath;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return imageUrl;
    }

    public static String convertStringToSlug(String str) {
        String slug = "";
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            slug = pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception e) {
            e.printStackTrace();
            slug = "";
        }
        return slug;
    }
}