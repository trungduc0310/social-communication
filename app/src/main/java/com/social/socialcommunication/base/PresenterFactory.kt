package com.social.socialcommunication.base

import java.util.*

class PresenterFactory {
    private var mRegisterPresenters = HashMap<String, Class<*>>()

    companion object {
        private val TAG = "Create Presenter"
        private var instance: PresenterFactory? = null
        fun getInstance(): PresenterFactory {
            if (instance == null) {
                synchronized(PresenterFactory::class) {
                    instance = PresenterFactory()
                }
            }
            return instance!!
        }
    }

    fun registerPresenter(presenterId: String, clazz: Class<*>): PresenterFactory {
        if (!mRegisterPresenters.containsKey(presenterId)) {
            mRegisterPresenters[presenterId] = clazz
        }
        return this
    }

    @Synchronized
    fun createPresenter(
        presenterId: String,
        baseView: BaseViewOps
    ): BasePresenterOps? {
        val clazz = mRegisterPresenters[presenterId]
        var `object`: BasePresenter<*>? = null
        try {
            `object` = clazz!!.newInstance() as BasePresenter<*>
            `object`.takeView(baseView)
        } catch (e: InstantiationException) {
            //Logger.e(TAG, e.getMessage(), e);
        } catch (e: IllegalAccessException) {
        } catch (e: NullPointerException) {
        }
        return `object`
    }
}