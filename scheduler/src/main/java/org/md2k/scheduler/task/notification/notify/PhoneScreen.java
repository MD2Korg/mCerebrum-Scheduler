package org.md2k.scheduler.task.notification.notify;

import android.content.Context;
import android.os.PowerManager;

import org.md2k.datakitapi.time.DateTime;
import org.md2k.scheduler.task.notification.Notification;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;

/*
 * Copyright (c) 2016, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class PhoneScreen {
    private static final String TAG = PhoneScreen.class.getSimpleName();
    private PowerManager.WakeLock wl=null;

    Observable<String> getObservable(Context context, Notification notification){
        long interval = DateTime.getTimeInMillis(notification.getInterval());
        int repeat = notification.getRepeat();
        long startTime = DateTime.getDateTime();

        return Observable.from(notification.getWhen())
                .map(s -> {
                    long delayOffset = DateTime.getTimeInMillis(s);
                    return (startTime + delayOffset) - DateTime.getDateTime();
                }).flatMap(new Func1<Long, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Long delay) {
                        if(delay<=0L) delay=1L;
                        return Observable.interval(delay,interval, TimeUnit.MILLISECONDS).takeWhile(aLong -> {
                            if(aLong<repeat) return true;
                            else return false;
                        });
                    }
                }).map(integer -> {
                    if(integer%2==1) screenOn(context);
                    else
                        screenOff();
                    return "";
                }).onErrorReturn(throwable -> {
                    screenOff();
                    return null;
                }).filter(s -> false).doOnError(throwable -> screenOff()).doOnUnsubscribe(this::screenOff);
    }
    private void screenOn(Context context){
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        wl.acquire();
    }
    private void screenOff(){
        if(wl!=null)
            wl.release();
        wl=null;
    }
}
