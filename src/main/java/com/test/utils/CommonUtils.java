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
        String jwtToken = "eyJraWQiOiI1bHAwbFd3aGdsRlRFM21LTlRydTQ1U2I5UFZ0IiwidHlwIjoiSldUIiwiYWxnIjoiUlMyNTYifQ.eyJjcmVhdGVkQXQiOjE2Njk4OTM5OTAxMTUsInN1YnNjcmlwdGlvbnMiOlt7InN1Yk5hbWUiOiJpbmxhbmQiLCJzdWJJZCI6IjRiN2E3ZjUzLTAyZDctNGI0OC1hZTFjLTc3NGYyZmZkNmZhNSIsInJvbGVzIjoiUmVhZGVyIn1dLCJhcHBQZXJtaXNzaW9ucyI6bnVsbCwicmVzb3VyY2UiOlt7InJlc05hbWUiOiJhYmMxMjN0ZXN0IiwicmVzSWQiOiI0ZmUwOGI3Ni1kYjdmLTRiMGItOWYyOS1kYWE3MmU3YjcxOGYiLCJyb2xlcyI6W3sibmFtZSI6InN1cGVyIGFkbWluIHJvbGUifV19XSwiaXNzIjoiaHR0cHM6Ly9hbmNob3ItcmJhYy1yZXRyaWV2YWwtYXBwLmNkdC5tYWVyc2stZGlnaXRhbC5uZXQiLCJncm91cHMiOlsiZWFkNDZiZTktZDg3Mi00ZmQzLTkyMTYtY2U1OGNiN2Y2N2FlIl0sImV4cCI6MTY2OTk3Njk4NywiaWF0IjoxNjY5OTczMzg3LCJlbWFpbCI6ImRoYW5hbmpheWEuc2luZ2hhckBtYWVyc2suY29tIn0.EnG808a7hlw81CMRNLwU7H66H3sU2-gpOMThsfyH_OoRJkqYzZzoiOEPJ7AvE1egPYok9up_vuMXiWuX7QaPj9XRN0k99-DouHSjoOHIdDoQ8jp-oimnxSH_F94-2A6rZSQKSMr3uNxFonF2hYnD0-DM5pozA7-OM7FdfjcGNmDI5PZ5u8emxEPKs5eJjiwbzqbMciNnlqhMwasbOa9Pm8F9Qeth1ercTPjsJPvDzLDTyZj78oetndMskPBJzk6vUEIl8y3XoAZGy_WFMGPuLSbzXEx9oSSN9sfGin6bC2_V0GOa6FkfZDjZKGliycHk5Xpd4AGNS_kRiLzh-z2Ciw";
        // String jwtToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJlZGIwYmYxMC0yNzEwLTQ0NDAtYTIyNS02MzcxNDMyYjExMGUiLCJpc3MiOiJodHRwczovL2xvZ2luLm1pY3Jvc29mdG9ubGluZS5jb20vMDVkNzVjMDUtZmExYS00MmU3LTljZjEtZWI0MTZjMzk2ZjJkL3YyLjAiLCJpYXQiOjE2Njk5NzU0NDEsIm5iZiI6MTY2OTk3NTQ0MSwiZXhwIjoxNjY5OTgwOTMyLCJhaW8iOiJBWFFBaS84VEFBQUFCZ0lpMndvY0hWb21vUUtnUDNkd2Z6Rm9DWE9JcS8xUmZscHpMQ2FkQTF3OHZVcjhxWWpPMmNyekRsU0JDRHU1cURNNHB1Uk5BYnh0RjhPNE1LNWZBdDB2QWZNRWRJTFhaOTNTd083VWExQm1KN3Z1OHUwSHlqVUE4TFhrcFlQcEdlbFd6YVduOTV2WklURGhTOHNqVkE9PSIsImF6cCI6ImVkYjBiZjEwLTI3MTAtNDQ0MC1hMjI1LTYzNzE0MzJiMTEwZSIsImF6cGFjciI6IjEiLCJlbWFpbCI6ImRoYW5hbmpheWEuc2luZ2hhckBtYWVyc2suY29tIiwibmFtZSI6IkRoYW5hbmpheWEgU2FtYW50YSBTaW5naGFyIiwib2lkIjoiYmI2Y2M3OTgtYmMzNC00MzY5LTliMmEtN2YyMDRkZjdiMDc4IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiZGhhbmFuamF5YS5zaW5naGFyQG1hZXJzay5jb20iLCJyaCI6IjAuQVJBQUJWelhCUnI2NTBLYzhldEJiRGx2TFJDX3NPMFFKMEJFb2lWamNVTXJFUTRRQUVVLiIsInNjcCI6IkFQSVNjb3BlIiwic3ViIjoiMWtmcTVSODZ4cGRLWF9heEl1WlNKbjlPZ2pYVXNQaGZjdHVQVDE2cGR2ZyIsInRpZCI6IjA1ZDc1YzA1LWZhMWEtNDJlNy05Y2YxLWViNDE2YzM5NmYyZCIsInV0aSI6Ii1TX0xtYmdza0V5Q29qWmdQdlg3QUEiLCJ2ZXIiOiIyLjAifQ.ghtLil1TfZ2zCbsfZWUE8ihDKc10y5boWK3Hwwrtn2v1AfcCUM--ACXsZMgoxxGu6qy0lcWRtjAFcAIydax3ANLVTY1RV39ds7RVAE17VPRQUAVt5n34KEePwQtdWkYRZDZ2LftWDdUPoVioHdqchCNG6RCRsybsq2Es9Fk_Fbchqhinq3HKYUztWlHRZiKaklfv-hRMxBmxAcIsxTAnZhy6Ur2lCImArbSkvggmztLbOA122iopVOUvh9JqDVGg5ubtsSqyppCQ0tVT-5LPtbOP0Vx8rcWhsyHOO9tT6xgZuEyV7FOyKJnMZXJqdSXoRx_2ymVOZJFNTozGZkB1HQ";
        //String jwtToken = "eyJ0eXAiOiJKV1QiLCJraWQiOiJ3VTNpZklJYUxPVUFSZVJCL0ZHNmVNMVAxUU09IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJtYWVyc2tSZXN0QXBpIiwiY3RzIjoiT0FVVEgyX1NUQVRFTEVTU19HUkFOVCIsImF1ZGl0VHJhY2tpbmdJZCI6ImM0YzdkZmI3LWExYWMtNDI1NS04ZmUxLTM0NDEwMmExN2VhNC0xMDgzMzkiLCJpc3MiOiJodHRwczovL2lhbS1jZHQubWFlcnNrLmNvbS9hY20vb2F1dGgyL21hdSIsInRva2VuTmFtZSI6ImFjY2Vzc190b2tlbiIsInRva2VuX3R5cGUiOiJCZWFyZXIiLCJhdXRoR3JhbnRJZCI6Im52eHhxNGprbDhqMVBuVDVsUnl4emJrb0NrMCIsImF1ZCI6Im1hZXJza1Jlc3RBcGkiLCJuYmYiOjE2Njk5NzU2MjAsImdyYW50X3R5cGUiOiJjbGllbnRfY3JlZGVudGlhbHMiLCJzY29wZSI6WyJnY3NzLnVzZXIuYWRtaW4iLCJvcGVuaWQiLCJvcmcubWFlcnNrLmFwcC5vcGVyYXRvciJdLCJhdXRoX3RpbWUiOjE2Njk5NzU2MjAsInJlYWxtIjoiL21hdSIsImV4cCI6MTY2OTk4MjgyMCwiaWF0IjoxNjY5OTc1NjIwLCJleHBpcmVzX2luIjo3MjAwLCJqdGkiOiJfdU1NRmdiZzRxN3Z5OWVKNm5BaXJRU0poRjQifQ.LU_OSgvoO89FzZt9-83IYIYmP4rGYqQ2zjtxlPd0muw47rg66fr7D-h1MlgMPPigoToC16ojQ9Cj-DAj1ydicGykK-FaFvAdBvGts93IsbDWl_7KBNvBOLtpMYY1bTI79q6NWFgE3cP8zO0JoZYuhe8bCVIYJNxjsNtpA8WT6w5UDjvfhn0JFhCF_xYkHUBS-3f0oqPE8zrKozoo2VxxX1enHytCcyFi0PIoa9L42S-AKulblFYoN5hKWMeKKH8DF3LhSh2qKvoCgpgEr0FaTtxNGeUn6Oti0lSZFcgB8WjIkjn3oVFI0mrh7uSTHc81VBSVVJfhM0eGjKlmJgCRjg";

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
