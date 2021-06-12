package com.example.donelistapp.api;

public class UtilsApi {

    public static final String BASE_URL_API = "https://donelist.000webhostapp.com/";

    public static BaseApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiService.class);
    }

}
