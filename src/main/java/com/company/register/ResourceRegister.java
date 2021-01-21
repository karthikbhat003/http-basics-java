package com.company.register;

import com.company.service.HttpService;

public class ResourceRegister {
    private HttpService httpService;

    public HttpService getClazz(){
        if(httpService == null){
            return new HttpService();
        }
        return httpService;
    }
}
