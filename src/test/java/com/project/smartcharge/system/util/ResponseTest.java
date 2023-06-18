/**
 * @author Valar Morghulis
 * @Date 2023/6/4
 */
package com.project.smartcharge.system.util;

import com.project.smartcharge.system.respon.Response;
import org.junit.jupiter.api.Test;

public class ResponseTest {
    @Test
    public void reponseTest(){
        System.out.println(Response.SimpleResponse.generate(true, 200, "1233"));
    }
}
