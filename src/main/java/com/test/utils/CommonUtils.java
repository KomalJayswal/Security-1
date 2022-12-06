package com.test.utils;

public class CommonUtils {

    public static String getTokenType(String jwtToken) {
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String[] parts = jwtToken.split("\\.");
        String body = new String(decoder.decode(parts[1]));
        if (body.contains(Constants.AZURE_TOKEN)) {
            return Constants.AZURE_TOKEN;
        }
        if (body.contains(Constants.FORGEROCK_TOKEN)) {
            return Constants.FORGEROCK_TOKEN;
        }
        return "";
    }


        public static void main(String[] args) {
        String jwtToken = ""; //Enter any token
        System.out.println("------------ Decode JWT ------------");
        System.out.println("~~~~~~~~~ JWT Header ~~~~~~~");
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)

        String header = new String(decoder.decode(parts[0]));
        String body = new String(decoder.decode(parts[1]));
        System.out.println("JWT Header : " + header);

        System.out.println("JWT Body : " + body);

        if (body.contains("microsoft")) {
            System.out.println("Azure Token");
        }

        if (body.contains("iam-cdt.maersk.com")) {
            System.out.println("ForgeRock Token");
        }
    }
}
