package me.zt.illusion.util

/**
 * Created by tian on 11/07/2018.
 * 
 */
class Flag {

    private var mFlag = 0

    constructor()
    constructor(mFlag: Int) {
        this.mFlag = mFlag
    }


    fun set(flag: Int) { mFlag = flag }
    fun has(flag: Int): Boolean = mFlag.and(flag) == flag
    fun del(flag: Int) { mFlag = mFlag.and(flag.inv()) }
    fun add(flag: Int) { mFlag = mFlag.or(flag) }
    fun cls() { mFlag = 0 }
    fun all() { mFlag = 0.inv() }
    fun get(): Int = mFlag

    override fun toString(): String = "Flag:${Integer.toBinaryString(mFlag)}"
}