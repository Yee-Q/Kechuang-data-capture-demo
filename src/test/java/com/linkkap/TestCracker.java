package com.linkkap;

import org.junit.Test;

import java.io.IOException;

/**
 * @author yeeq
 * @date 2020/10/29
 */
public class TestCracker {

    private Cracker cracker = new Cracker();

    @Test
    public void testGetDataList() throws IOException {
        cracker.getDataList();
    }

//    @Test
//    public void testGetProspectus() throws IOException {
//        cracker.getProspectus();
//    }

//    @Test
//    public void testDownLoadByUrl() throws IOException {
//        cracker.downLoadByUrl();
//    }
}
