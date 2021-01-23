package com.company;

import java.io.IOException;
import com.company.register.ResourceRegister;

public class HttpApplication {
    public static void main(String[] args) throws IOException {
        final ResourceRegister resourceRegister = new ResourceRegister();
        resourceRegister.getClazz().gitYamlGetCall();
    }
}
