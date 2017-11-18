#include <jni.h>
#include <string.h>
#include <string>
#include <stdlib.h>
#include <android/log.h>

#define LOG_TAG "jni"
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define LOGD(...)  __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

//将a强制声明为指向整数的指针，读取该指针指向的整数
int compare(const void* a, const void* b){
    return (*(int*)a-*(int*)b);
}

/**
 * 做成员变量的初始化
 */
extern "C"//混合编程
JNIEXPORT void JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_init(JNIEnv *env, jclass type) {

    jclass _jclass = type;
    jfieldID _jfiledID = NULL;//局部静态变量(第一种办法)
    if (_jfiledID == NULL){
        _jfiledID = env->GetFieldID(_jclass,"name","Ljava/lang/String;");
        LOGI("----------GetFiledID------");
    }


}

extern "C"//混合编程
JNIEXPORT void JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_cachede(JNIEnv *env, jobject instance) {

    jclass _jclass = env->GetObjectClass(instance);
    static jfieldID _jfiledID = NULL;//局部静态变量(第一种办法)
    if (_jfiledID == NULL){
        _jfiledID = env->GetFieldID(_jclass,"name","Ljava/lang/String;");
        LOGI("----------GetFiledID------");
    }
}

extern "C"//混合编程
JNIEXPORT void JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_exception(JNIEnv *env, jobject instance) {

    //构建一个异常
    jclass _jclass = env->GetObjectClass(instance);
    jfieldID _jfiledID = env->GetFieldID(_jclass,"name2","Ljava/lang/String;");
    //检测异常
    jthrowable _jthrowable = env->ExceptionOccurred();
    if(_jthrowable!=NULL){
        //为了保证java代码能继续执行，我们要清除异常
        env->ExceptionClear();
        _jfiledID = env->GetFieldID(_jclass,"name","Ljava/lang/String;");
    }
    jstring _jstring = (jstring) env->GetObjectField(instance, _jfiledID);
    char* str = (char *) env->GetStringUTFChars(_jstring, NULL);
    //假使c++有异常，java层面如何try
    if (strcmp(str,"www")!=0){
        //抛出异常  --> java异常
        jclass  newThrow = env->FindClass("java/lang/IllegalArgumentException");
        env->ThrowNew(newThrow,"非法参数");

    }

}

extern "C"//混合编程
JNIEXPORT void JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_getLocalReference(JNIEnv *env, jobject instance) {

    //模拟一个循环
    for(int i=0;i<100;i++){
        jclass _jclass = env->FindClass("java/util/Date");
        jobject _jobj = env->NewObject(_jclass,env->GetMethodID(_jclass,"<init>","()V"));
        //对对象进行操作
        env->DeleteLocalRef(_jobj);//删除局部变量
    }

}

extern "C"//混合编程
JNIEXPORT void JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_getArray(JNIEnv *env, jobject instance,
                                                        jintArray arrays_) {
    jint *arrays = env->GetIntArrayElements(arrays_, NULL);
    //获取数组长度
    int len = env->GetArrayLength(arrays_);
    //排序
    // qsort(void* __base, size_t __nmemb, size_t __size, int (*__comparator)(const void* __lhs, const void* __rhs));
    qsort(arrays,len, sizeof(int),compare);//头文件stdlib.h中的方法

    env->ReleaseIntArrayElements(arrays_, arrays, 0);//释放
}

extern "C"//混合编程
JNIEXPORT jstring JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_getMessage(JNIEnv *env, jobject instance) {
    //1.拿到jclass
    jclass _jclass = env->GetObjectClass(instance);
    //2.拿到jmethodID
    //jmethodID GetMethodID(jclass clazz, const char* name, const char* sig)-->jclass,需要访问的方法名，签名
    jmethodID _jmethodID = env->GetMethodID(_jclass,"getName","()Ljava/lang/String;");
    //jni 调用java的方法
    jstring result = (jstring) env->CallObjectMethod(instance, _jmethodID);
    char* str= (char *) env->GetStringUTFChars(result, NULL);
    char text[20] = "success";
    char* finresult = strcat(str,text);
    return env->NewStringUTF(finresult);
}

extern "C"//混合编程
JNIEXPORT jstring JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_updateNameFromc(JNIEnv *env, jobject instance) {
    //jobject代表的是什么？-->ManiActivity这个类的对象(MainActivity调用的它),首先获取jclass
    jclass _jclass = env->GetObjectClass(instance);//通过上下文，获取到jclass
    //jfieldID GetFieldID(jclass clazz, const char* name, const char* sig)-->jclass,我们需要访问的属性名，签名
    jfieldID _jfieldID = env->GetFieldID(_jclass,"name","Ljava/lang/String;");
    //jobject GetObjectField(jobject obj, jfieldID fieldID)-->instance,jfieldID
    jstring result = (jstring) env->GetObjectField(instance, _jfieldID);
    printf("%#x\n",result);
    //如何转化为java中的String类型
    char* str= (char *) env->GetStringUTFChars(result, NULL);
    char text[20] = "success";
    char* finresult = strcat(str,text);
    return env->NewStringUTF(finresult);
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_vincent_ndkdemo1_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";



    return env->NewStringUTF(hello.c_str());
}
