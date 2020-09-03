package com.kotlin.andi.fundamentalandroid

import android.os.AsyncTask


interface LoadingImplementation {
    fun loading()
}

class LoadingView(private val listener: LoadingImplementation): AsyncTask<Void, Void, Void>() {
    override fun doInBackground(vararg params: Void?): Void? {
        for (i in 0 until 3 ) {
            Thread.sleep(1000)
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        listener.loading()
    }

}