package me.zt.illusion.util

import java.lang.reflect.Field

/**
 * Created by tian on 17/04/2019.
 *
 */
object JavaUtil {

    fun getField(cls: Class<*>, fieldName: String): Field?{
        try{
            return cls.getDeclaredField(fieldName)
        }catch (e: Exception){
            throw e
//            Toolkit.handleError("", e, "JavaUtil, getField")
        }
//        return null
    }

    @Deprecated("", replaceWith = ReplaceWith("getFieldValue(cls: Class<*>, obj: Any, fieldName: String)"))
    fun <T> getFieldValue(obj: Any, fieldName: String): T?{
        return getFieldValue(obj::class.java, obj, fieldName)
    }

    fun <T> getFieldValue(cls: Class<*>, obj: Any, fieldName: String): T?{
        try{
            val field = getField(cls, fieldName) ?: return null
            field.isAccessible = true
            return field.get(obj) as? T
        }catch (e: Exception){
            if(Client.isTest()) throw e
        }
        return null
    }

    fun setFieldValue(cls: Class<*>, obj: Any, fieldName: String, value: Any?): Boolean{
        try{
            val field = getField(cls, fieldName) ?: return false
            field.isAccessible = true
            field.set(obj, value)
            return true
        }catch (e: Exception){
            if(Client.isTest()) throw e
        }
        return false
    }

    fun newInstance(name: String): Any?{
        try{
            val cls = Class.forName(name)
            return cls.newInstance()
        }catch (e: Exception){
            throw e
//            Toolkit.handleError("", e, "JavaUtil, setFieldValue")
        }
//        return null
    }

    @Deprecated("")
    fun invokeSimply(cls: Class<*>, obj: Any, methodName: String, vararg args: Any): Boolean{
        val clsArgs = Array<Pair<Class<*>, Any?>>(args.size){i->
            args[i].let { (it::class.javaPrimitiveType?:it::class.java) to it }
        }
        return invokeInternal(false, cls, obj, methodName, *clsArgs)?:false
    }

    fun invoke(cls: Class<*>, obj: Any, methodName: String, vararg args: Pair<Class<*>, Any?>): Boolean{
        return invokeInternal(false, cls, obj, methodName, *args)?:false
    }

    @Deprecated("")
    fun <T> callSimply(cls: Class<*>, obj: Any, methodName: String, vararg args: Any): T?{
        val clsArgs = Array<Pair<Class<*>, Any?>>(args.size){i->
            args[i].let { (it::class.javaPrimitiveType?:it::class.java) to it }
        }
        return invokeInternal(true, cls, obj, methodName, *clsArgs)
    }

    fun <T> call(cls: Class<*>, obj: Any, methodName: String, vararg args: Pair<Class<*>, Any?>): T?{
        return invokeInternal(true, cls, obj, methodName, *args)
    }

    private fun <T> invokeInternal(hasReturn: Boolean, cls: Class<*>, obj: Any, methodName: String, vararg args: Pair<Class<*>, Any?>): T?{
        var result : Any? = null
        try{
            val clsArgs = args.map { it.first }.toTypedArray()
            val method = cls.getDeclaredMethod(methodName, *clsArgs)
            if(method != null){
                method.isAccessible = true
                val valueArgs = args.map { it.second }.toTypedArray()
                result = if(hasReturn){
                    method.invoke(obj, *valueArgs)
                }else{
                    method.invoke(obj, *valueArgs)
                    true
                }
            }
        }catch (e: Exception){
            throw e
//            Toolkit.handleError("", e, "JavaUtil, invokeMethod")
        }
        return result as? T
    }
}