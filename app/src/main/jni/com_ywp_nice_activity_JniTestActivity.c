/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_ywp_nice_activity_JniTestActivity */

#ifndef _Included_com_ywp_nice_activity_JniTestActivity
#define _Included_com_ywp_nice_activity_JniTestActivity
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_ywp_nice_activity_JniTestActivity
 * Method:    helloJni
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ywp_nice_activity_JniTestActivity_helloJni
  (JNIEnv *env, jclass jobj){

  return (*env)->NewStringUTF(env,"Hello Jni!");
  }

/*
 * Class:     com_ywp_nice_activity_JniTestActivity
 * Method:    addCalc
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_ywp_nice_activity_JniTestActivity_addCalc
  (JNIEnv *env, jclass jobj, jint ja, jint jb){
    return ja + jb;
  }

#ifdef __cplusplus
}
#endif
#endif
