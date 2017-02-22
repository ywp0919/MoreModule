// IRemoteService.aidl
package com.ywp.nice;

// 在这里导入这个类型,路径为这个类型的AIDL路径
import com.ywp.nice.HelloAIDL;
// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
//    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
//            double aDouble, String aString);

        HelloAIDL sayHelloAIDL();
}
